package com.food_del_pltfrm.user_service.services.interfaces;

import com.food_del_pltfrm.user_service.dtos.SignUpRequest;
import com.food_del_pltfrm.user_service.entities.User;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService {
    User createUser(SignUpRequest signUpRequest);
    User findByFullName(String fullName);
    User findByEmail(String email);
    User findById(Long id);
    boolean existsByUsername(String fullName);
    boolean existsByEmail(String email);
    boolean checkPassword(String password, String database_password);
    void saveUser(User user);
    UserDetails loadUserByUsername(String fullname);
}