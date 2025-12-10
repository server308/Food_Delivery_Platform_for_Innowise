package com.food_del_pltfrm.restaurant_service.dtos;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishUpdateDTO {
    private String name;
    private String description;
    private Integer price;
    private String imageUrl;
}
