package com.food_del_pltfrm.restaurant_service.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishDTO {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private String imageUrl;
}