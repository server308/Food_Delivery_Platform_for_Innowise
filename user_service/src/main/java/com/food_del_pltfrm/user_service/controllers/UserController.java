package com.food_del_pltfrm.user_service.controllers;

import com.food_del_pltfrm.user_service.dtos.UserDTO;
import com.food_del_pltfrm.user_service.dtos.UserUpdateDTO;
import com.food_del_pltfrm.user_service.dtos.AddressDTO;
import com.food_del_pltfrm.user_service.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        UserDTO user = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok(user);
    }


    /**
     * PUT /api/users/me - Update current user
     */
    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateCurrentUser(Authentication authentication, @RequestBody UserUpdateDTO userUpdateDTO){
        UserDTO currentUser = userService.findByEmail(authentication.getName());
        UserDTO updatedUser = userService.updateUser(currentUser.getId(), userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * DELETE /api/users/me - Delete current user
     */
    @PutMapping("/me")
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        UserDTO currentUser = userService.findByEmail(authentication.getName());
        userService.deleteUser(currentUser.getId());
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
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    /**
     * PUT /api/users/{id} - Update User
     * - Administrator: Can update any user
     * - User: Can only update their own profile
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDTO userUpdateDTO) {
        UserDTO updatedUser = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }



    /**
     * DELETE /api/users/{id} - Delete User
     * - Administrator: Can delete any user
     * - User: Can only delete their own account
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}