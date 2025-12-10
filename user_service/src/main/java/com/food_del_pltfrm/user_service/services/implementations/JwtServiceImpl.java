package com.food_del_pltfrm.user_service.services.implementations;

import com.food_del_pltfrm.user_service.dtos.*;
import com.food_del_pltfrm.user_service.entities.User;
import com.food_del_pltfrm.user_service.entities.VerificationCode;
import com.food_del_pltfrm.user_service.jwt.JwtTokenProvider;
import com.food_del_pltfrm.user_service.rabbit.EventPublisher;
import com.food_del_pltfrm.user_service.repositories.CodeRepository;
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

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserService userService;
    private final CodeRepository codeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final EventPublisher publisher;
    @Override
    public JwtResponse signUp(SignUpRequest signUpRequest) {
        Random rand = new Random();
        int randomNumber = 100000 + rand.nextInt(900000);
        String randomString = String.valueOf(randomNumber);
        User user = userService.createUser(signUpRequest);
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setUser(user);
        verificationCode.setCode(String.valueOf(randomNumber));
        verificationCode.setCreatedAt(LocalDateTime.now());
        verificationCode.setExpiresAt(LocalDateTime.now().plusHours(3));
        codeRepository.save(verificationCode);
        publisher.publishUserCreated(verificationCode, user);

        //String accessToken = jwtTokenProvider.generateAccessToken(user.getId().toString(), user.getEmail(), user.getRoles().stream().map(role -> role.getName()).toList());
        //String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId().toString(), user.getEmail());
      //  return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse signIn(SignInRequest signInRequest) {
        User user = userService.getUserForToken(signInRequest.getEmail());
        if (!userService.checkPassword(signInRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Access denied! Invalid password!");
        }
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId().toString(), user.getEmail(), user.getRoles().stream().map(role -> role.getName()).toList());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId().toString(), user.getEmail());
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

            String email = jwtTokenProvider.getEmailFromToken(refreshToken, true);
            log.info("Extracted fullName: {}", email);

            UserDTO user = userService.findByEmail(email);
            log.info("Found user: {}", user.getEmail());

            String newAccessToken = jwtTokenProvider.generateAccessToken(user.getId().toString(), user.getEmail(), user.getRoles());
            log.info("Generated new access token");

            return new AccessToken(newAccessToken);

        } catch (Exception e) {
            log.error("Error in getNewAccessToken: {}", e.getMessage(), e);
            throw new RuntimeException("Token refresh failed: " + e.getMessage());
        }
    }
}
