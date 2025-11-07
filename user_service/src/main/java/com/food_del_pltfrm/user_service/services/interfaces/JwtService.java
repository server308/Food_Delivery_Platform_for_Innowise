package com.food_del_pltfrm.user_service.services.interfaces;

import com.food_del_pltfrm.user_service.dtos.*;

public interface JwtService {
    JwtResponse signUp(SignUpRequest signUpRequest);

    JwtResponse signIn(SignInRequest signInRequest);
    AccessToken getNewAccessToken(RefreshToken refreshToken);
}
