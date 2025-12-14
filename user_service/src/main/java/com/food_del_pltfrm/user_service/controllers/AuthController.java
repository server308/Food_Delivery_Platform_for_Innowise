package com.food_del_pltfrm.user_service.controllers;

import com.food_del_pltfrm.user_service.dtos.*;
import com.food_del_pltfrm.user_service.entities.User;
import com.food_del_pltfrm.user_service.services.interfaces.JwtService;
import com.food_del_pltfrm.user_service.services.interfaces.UserService;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> signIn(@RequestBody SignInRequest signInRequest) throws AuthException {
        final JwtResponse token = jwtService.signIn(signInRequest);
        return ResponseEntity.ok(token);
    }


    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) throws AuthException {
        String message = jwtService.signUp(signUpRequest);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/code")
    public ResponseEntity<JwtResponse> checkCode(@RequestBody CodeDTO dto){
        JwtResponse jwtResponse = jwtService.verifyCodeAndGetTokens(dto);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/token")
    public ResponseEntity<AccessToken> getNewAccessToken(@RequestBody RefreshToken request) throws AuthException {
        final AccessToken token = jwtService.getNewAccessToken(request);
        return ResponseEntity.ok(token);
    }

}
