package com.food_del_pltfrm.user_service.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String fullName;
    private String password;
}
