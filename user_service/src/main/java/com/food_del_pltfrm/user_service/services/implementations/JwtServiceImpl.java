package com.food_del_pltfrm.user_service.services.implementations;

import com.food_del_pltfrm.user_service.dtos.*;
import com.food_del_pltfrm.user_service.entities.User;
import com.food_del_pltfrm.user_service.jwt.JwtTokenProvider;
import com.food_del_pltfrm.user_service.services.interfaces.JwtService;
import com.food_del_pltfrm.user_service.services.interfaces.UserService;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public JwtResponse signUp(SignUpRequest signUpRequest) {
        User user = userService.createUser(signUpRequest);
        String accessToken = jwtTokenProvider.generateAccessToken(user.getFullName(), user.getRoles());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getFullName());
        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse signIn(SignInRequest signInRequest) {
        User user = userService.findByFullName(signInRequest.getFullName());
        if (!userService.checkPassword(signInRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Access denied! Invalid password!");
        }
        String accessToken = jwtTokenProvider.generateAccessToken(user.getFullName(), user.getRoles());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getFullName());
        return new JwtResponse(accessToken, refreshToken);

    }

    @Override
    public AccessToken getNewAccessToken(RefreshToken refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();

            log.info("=== REFRESH TOKEN DEBUG ===");
            log.info("Refresh token: {}", refreshToken);

            boolean isValid = jwtTokenProvider.validateToken(refreshToken, true);
            log.info("Validation result: {}", isValid);

            if (!isValid) {
                try {
                    Claims claims = jwtTokenProvider.getAllClaimsFromToken(refreshToken, true);
                    log.info("Token claims: {}", claims);
                    log.info("Token expiration: {}", claims.getExpiration());
                    log.info("Current time: {}", new Date());
                } catch (Exception e) {
                    log.error("Failed to parse token: {}", e.getMessage());
                }
                throw new RuntimeException("Invalid refresh token");
            }

            String fullName = jwtTokenProvider.getFullNameFromToken(refreshToken, true);
            log.info("Extracted fullName: {}", fullName);

            User user = userService.findByFullName(fullName);
            log.info("Found user: {}", user.getUsername());

            String newAccessToken = jwtTokenProvider.generateAccessToken(user.getFullName(), user.getRoles());
            log.info("Generated new access token");

            return new AccessToken(newAccessToken);

        } catch (Exception e) {
            log.error("Error in getNewAccessToken: {}", e.getMessage(), e);
            throw new RuntimeException("Token refresh failed: " + e.getMessage());
        }
    }
}
