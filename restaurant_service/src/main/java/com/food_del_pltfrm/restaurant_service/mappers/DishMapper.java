package com.food_del_pltfrm.restaurant_service.mappers;

import com.food_del_pltfrm.restaurant_service.dtos.DishCreateDTO;
import com.food_del_pltfrm.restaurant_service.dtos.DishDTO;
import com.food_del_pltfrm.restaurant_service.dtos.DishUpdateDTO;
import com.food_del_pltfrm.restaurant_service.entities.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DishMapper {

    DishDTO toDto(Dish entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Dish toEntity(DishCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Dish toEntity(DishUpdateDTO dto);
}