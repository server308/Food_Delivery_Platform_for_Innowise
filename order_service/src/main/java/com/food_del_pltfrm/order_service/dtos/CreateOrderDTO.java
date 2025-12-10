package com.food_del_pltfrm.order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {
    private Long userId;
    private Long restaurantId;
    private List<CreateOrderItemDTO> items;
    private List<CreatePaymentDTO> payments;
}