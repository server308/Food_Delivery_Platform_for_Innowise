package com.food_del_pltfrm.user_service.services.implementations;

import com.food_del_pltfrm.user_service.dtos.SignUpRequest;
import com.food_del_pltfrm.user_service.entities.Role;
import com.food_del_pltfrm.user_service.entities.User;
import com.food_del_pltfrm.user_service.repositories.RoleRepository;
import com.food_del_pltfrm.user_service.repositories.UserRepository;
import com.food_del_pltfrm.user_service.services.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByFullName(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    @Override
    public boolean checkPassword(String password, String database_password) {
        return passwordEncoder.matches(password, database_password);
    }

    @Override
    public User createUser(SignUpRequest signUpRequest) {

        // Проверка на существование пользователя
        if (existsByUsername(signUpRequest.getFullName())) {
            throw new RuntimeException("Username already exists");
        }

        if (existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Создание нового пользователя
        User user = new User();
        user.setFullName(signUpRequest.getFullName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now().plusMinutes(1));
        List<Role> roles = roleRepository.findRoleByName("USER").get();
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User findByFullName(String fullname) {
        return userRepository.findByFullName(fullname)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + fullname));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
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
    public void saveUser(User user) {
        userRepository.save(user);
    }
}