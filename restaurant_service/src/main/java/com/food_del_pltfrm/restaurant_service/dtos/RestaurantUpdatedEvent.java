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
public class RestaurantUpdatedEvent {
    private Long restaurantId;
    private String name;
    private String cuisine;
    private String address;
    private LocalDateTime updatedAt;
}
