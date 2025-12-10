package com.food_del_pltfrm.restaurant_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private Long restaurantId;
    private Integer totalPrice;
    private LocalDateTime createdAt;
}
