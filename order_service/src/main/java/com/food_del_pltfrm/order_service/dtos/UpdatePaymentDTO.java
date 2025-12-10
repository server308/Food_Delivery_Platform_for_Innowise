package com.food_del_pltfrm.order_service.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentDTO {
    private Long id;
    private String method;
    private Integer amount;
    private String status;
}
