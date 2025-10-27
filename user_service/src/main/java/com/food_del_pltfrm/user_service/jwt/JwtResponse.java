package com.food_del_pltfrm.user_service.jwt;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {

    private String accessToken;

    private String refreshToken;
}
