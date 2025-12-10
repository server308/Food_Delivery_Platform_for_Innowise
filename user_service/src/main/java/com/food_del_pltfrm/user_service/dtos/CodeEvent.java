package com.food_del_pltfrm.user_service.dtos;

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
