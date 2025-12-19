package com.food_del_pltfrm.restaurant_service.services;

import com.food_del_pltfrm.restaurant_service.dtos.DishCreateDTO;
import com.food_del_pltfrm.restaurant_service.dtos.DishDTO;
import com.food_del_pltfrm.restaurant_service.dtos.DishUpdateDTO;
import com.food_del_pltfrm.restaurant_service.entities.Dish;
import com.food_del_pltfrm.restaurant_service.entities.Restaurant;
import com.food_del_pltfrm.restaurant_service.mappers.DishMapper;
import com.food_del_pltfrm.restaurant_service.repositories.DishRepository;
import com.food_del_pltfrm.restaurant_service.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishMapper dishMapper;


    @Transactional
    public DishDTO create(Long restaurantId, DishCreateDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Dish dish = dishMapper.toEntity(dto);
        dish.setRestaurant(restaurant);

        Dish savedDish = dishRepository.save(dish);
        DishDTO dishDTO = dishMapper.toDto(savedDish);


        return dishDTO;
    }

    @Transactional
    public DishDTO update(Long restaurantId, Long dishId, DishUpdateDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Dish dish = dishRepository.findDishByRestaurantAndId(restaurant, dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found"));

        dish.setName(dto.getName());
        dish.setDescription(dto.getDescription());
        dish.setPrice(dto.getPrice());
        dish.setImageUrl(dto.getImageUrl());

        Dish savedDish = dishRepository.save(dish);
        DishDTO dishDTO = dishMapper.toDto(savedDish);


        return dishDTO;
    }

    @Transactional
    public void delete(Long restaurantId, Long dishId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        Dish dish = dishRepository.findDishByRestaurantAndId(restaurant, dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found"));

        dishRepository.delete(dish);

    }
    public DishDTO get(Long dishId) {
        return dishMapper.toDto(
                dishRepository.findById(dishId)
                        .orElseThrow(() -> new RuntimeException("Dish not found"))
        );
    }

    public List<DishDTO> getAllByRestaurant(Long restaurantId) {
        return dishRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(dishMapper::toDto)
                .collect(Collectors.toList());
    }
}
