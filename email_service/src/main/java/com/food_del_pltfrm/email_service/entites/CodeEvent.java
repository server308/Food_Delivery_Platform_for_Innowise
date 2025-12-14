package com.food_del_pltfrm.email_service.entites;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeEvent {

    private String code;
    private String email;
    private Long user_id;
}

