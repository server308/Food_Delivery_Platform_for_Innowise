package com.food_del_pltfrm.order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdatedEvent {
    private Long orderId;
    private Long userId;
    private String status;
    private LocalDateTime updatedAt;
}