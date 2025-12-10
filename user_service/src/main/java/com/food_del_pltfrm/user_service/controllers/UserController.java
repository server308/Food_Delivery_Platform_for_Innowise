package com.food_del_pltfrm.user_service.controllers;

import com.food_del_pltfrm.user_service.dtos.UserDTO;
import com.food_del_pltfrm.user_service.dtos.UserUpdateDTO;
import com.food_del_pltfrm.user_service.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /**
     * GET /api/users/me - Get current user
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getPersonalUser(Authentication authentication) {
        UserDTO user = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok(user);
    }


    /**
     * PUT /api/users/me - Update current user
     */
    @PutMapping("/me")
    public ResponseEntity<UserDTO> updatePersonalUser(Authentication authentication, @RequestBody UserUpdateDTO userUpdateDTO){
        UserDTO updatedUser = userService.updateUser(authentication.getName(), userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * DELETE /api/users/me - Delete current user
     */
    @DeleteMapping("/me")
    public ResponseEntity<Void> deletePersonalUser(Authentication authentication) {
        userService.deleteUser(authentication.getName());
        return ResponseEntity.noContent().build();
    }


    /**ADMIN ENDPOINTS*/


    /**
     * GET /api/users/{id} - Get user by ID
     * - Administrator: Can get any user
     * - User: Can only get their own profile
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * GET /api/users - Get all users (ADMIN ONLY)
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * GET /api/users/email/{email} - Get a user by email
     * - Administrator: Can get any user
     * - User: Can only get their own profile
     */
    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    /**
     * PUT /api/users/{email} - Update User
     * - Administrator: Can update any user
     * - User: Can only update their own profile
     */
    @PutMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String email,
            @RequestBody UserUpdateDTO userUpdateDTO) {
        UserDTO updatedUser = userService.updateUser(email, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }



    /**
     * DELETE /api/users/{email} - Delete User
     * - Administrator: Can delete any user
     * - User: Can only delete their own account
     */
    @DeleteMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }


}