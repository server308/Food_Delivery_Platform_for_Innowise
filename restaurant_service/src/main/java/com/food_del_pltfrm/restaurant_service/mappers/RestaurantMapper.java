package com.food_del_pltfrm.restaurant_service.mappers;

import com.food_del_pltfrm.restaurant_service.dtos.RestaurantCreateDTO;
import com.food_del_pltfrm.restaurant_service.dtos.RestaurantDTO;
import com.food_del_pltfrm.restaurant_service.dtos.RestaurantUpdateDTO;
import com.food_del_pltfrm.restaurant_service.entities.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DishMapper.class)
public interface RestaurantMapper {

    RestaurantDTO toDto(Restaurant entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dishes", ignore = true)
    Restaurant toEntity(RestaurantCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dishes", ignore = true)
    Restaurant toEntity(RestaurantUpdateDTO dto);
}