package com.food_del_pltfrm.user_service.services.implementations;

import com.food_del_pltfrm.user_service.dtos.*;
import com.food_del_pltfrm.user_service.entities.User;
import com.food_del_pltfrm.user_service.jwt.JwtTokenProvider;
import com.food_del_pltfrm.user_service.services.interfaces.JwtService;
import com.food_del_pltfrm.user_service.services.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        if (userService.checkPassword(signInRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Access denied! Invalid password!");
        }
        String accessToken = jwtTokenProvider.generateAccessToken(user.getFullName(), user.getRoles());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getFullName());
        return new JwtResponse(accessToken, refreshToken);

    }

    @Override
    public AccessToken getNewAccessToken(RefreshToken refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken.getRefreshToken(), true)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String fullName = jwtTokenProvider.getFullNameFromToken(refreshToken.getRefreshToken(), true);

        User user = userService.findByFullName(fullName);

        String newAccessToken = jwtTokenProvider.generateAccessToken(user.getFullName(), user.getRoles());

        return new AccessToken(newAccessToken);
    }
}
