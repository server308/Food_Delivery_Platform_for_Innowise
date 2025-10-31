package com.food_del_pltfrm.user_service.services.interfaces;

import com.food_del_pltfrm.user_service.dtos.SignUpRequest;
import com.food_del_pltfrm.user_service.entities.User;

public interface UserService {
    User createUser(SignUpRequest signUpRequest);
    User findByUsername(String fullName);
    User findByEmail(String email);
    User findById(Long id);
    boolean existsByUsername(String fullName);
    boolean existsByEmail(String email);
    void saveUser(User user);
}