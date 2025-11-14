package com.food_del_pltfrm.user_service.controllers;

import com.food_del_pltfrm.user_service.entities.User;
import com.food_del_pltfrm.user_service.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(Authentication authentication){
        String username = authentication.getName();
        User user = userService.findByFullName(username);
        return ResponseEntity.ok(user);
    }
}
