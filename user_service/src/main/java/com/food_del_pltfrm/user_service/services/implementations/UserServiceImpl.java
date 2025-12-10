package com.food_del_pltfrm.user_service.services.implementations;

import com.food_del_pltfrm.user_service.dtos.SignUpRequest;
import com.food_del_pltfrm.user_service.dtos.UserDTO;
import com.food_del_pltfrm.user_service.dtos.UserUpdateDTO;
import com.food_del_pltfrm.user_service.entities.Role;
import com.food_del_pltfrm.user_service.entities.User;
import com.food_del_pltfrm.user_service.mappers.UserMapper;
import com.food_del_pltfrm.user_service.rabbit.EventPublisher;
import com.food_del_pltfrm.user_service.repositories.RoleRepository;
import com.food_del_pltfrm.user_service.repositories.UserRepository;
import com.food_del_pltfrm.user_service.services.interfaces.UserService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.transaction.annotation.Transactional;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EventPublisher eventPublisher;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    @Override
    public boolean checkPassword(String password, String database_password) {
        return passwordEncoder.matches(password, database_password);
    }

    @Override
    @Transactional
    public User createUser(SignUpRequest signUpRequest) {

        if (existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFullName(signUpRequest.getFullName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now().plusMinutes(1));
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        List<Role> roles = List.of(role);
        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        UserDTO userDTO = userMapper.toUserDto(savedUser);


        log.info("User registered and event published: {}", savedUser.getEmail());
        return savedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByFullName(String fullname) {
        return userRepository.findByFullName(fullname)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + fullname));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        UserDTO dto = userMapper.toUserDto(user);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        UserDTO dto = userMapper.toUserDto(user);
        return dto;
    }



    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByFullName(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDTO updateUser(String email, UserUpdateDTO userUpdateDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));


        // Проверка email на уникальность (если email меняется)
        if (userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userUpdateDto.getEmail())) {
                throw new RuntimeException("Email " + userUpdateDto.getEmail() + " is already taken");
            }
        }

        // Обновление базовых полей (доступно всем)
        updateBasicUserInfo(user, userUpdateDto);

        if (userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }

        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);

        UserDTO userDTO = userMapper.toUserDto(updatedUser);

        log.info("✏️ User updated and event published: {}", email);

        return userDTO;
    }


    private void updateBasicUserInfo(User user, UserUpdateDTO userUpdateDto) {
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getFullName() != null) {
            user.setFullName(userUpdateDto.getFullName());
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userMapper.toUserDtoList(userRepository.findAll());
    }


    @Override
    @Transactional(readOnly = true)
    public User getUserForToken(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + email));
        Long userId = user.getId();
        // Удаляем пользователя
        userRepository.delete(user);

        log.info("🗑️ User deleted and event published: {} (ID: {})", user.getEmail(), userId);
    }
}