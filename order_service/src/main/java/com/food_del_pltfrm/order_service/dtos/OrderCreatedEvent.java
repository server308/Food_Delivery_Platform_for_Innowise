package com.food_del_pltfrm.order_service.dtos;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private Long restaurantId;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItemEvent> items;
}
