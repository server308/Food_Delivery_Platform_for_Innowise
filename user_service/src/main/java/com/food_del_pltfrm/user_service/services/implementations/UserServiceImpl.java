package com.food_del_pltfrm.user_service.services.implementations;

import com.food_del_pltfrm.user_service.dtos.SignUpRequest;
import com.food_del_pltfrm.user_service.entities.User;
import com.food_del_pltfrm.user_service.repositories.UserRepository;
import com.food_del_pltfrm.user_service.services.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Инъекция через конструктор (рекомендуется)
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
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
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }*/
}