package com.food_del_pltfrm.restaurant_service.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDTO {
    private Long id;
    private String name;
    private String cuisine;
    private String address;
    private List<DishDTO> dishes;
}