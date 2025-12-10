package com.food_del_pltfrm.restaurant_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishCreateDTO {
    private String name;
    private String description;
    private Integer price;
    private String imageUrl;
}