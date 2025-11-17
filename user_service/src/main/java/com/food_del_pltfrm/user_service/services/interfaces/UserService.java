package com.food_del_pltfrm.user_service.services.interfaces;

import com.food_del_pltfrm.user_service.dtos.SignUpRequest;
import com.food_del_pltfrm.user_service.dtos.UserDTO;
import com.food_del_pltfrm.user_service.dtos.UserUpdateDTO;
import com.food_del_pltfrm.user_service.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public interface UserService {
    User createUser(SignUpRequest signUpRequest);
    User findByFullName(String fullName);
    UserDTO findByEmail(String email);
    UserDTO getUserById(Long id);
    boolean existsByUsername(String fullName);
    boolean existsByEmail(String email);
    boolean checkPassword(String password, String database_password);
    void saveUser(User user);
    UserDetails loadUserByUsername(String fullname);
    List<UserDTO> getAllUsers();
    User getUserForToken(String email);
    UserDTO updateUser(Long userId, UserUpdateDTO userUpdateDto);

    void deleteUser(Long id);
}