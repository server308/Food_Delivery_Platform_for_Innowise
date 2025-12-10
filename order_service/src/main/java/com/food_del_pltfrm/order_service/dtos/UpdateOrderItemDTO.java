package com.food_del_pltfrm.order_service.dtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderItemDTO {
    private Long id;
    private Long dishId;
    private Integer quantity;
    private Integer price;
}
