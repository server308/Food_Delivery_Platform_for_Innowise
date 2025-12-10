package com.food_del_pltfrm.restaurant_service.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCreateDTO {
    private String name;
    private String cuisine;
    private String address;
}
