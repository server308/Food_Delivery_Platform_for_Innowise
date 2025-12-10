package com.food_del_pltfrm.restaurant_service.repositories;

import com.food_del_pltfrm.restaurant_service.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByRestaurantId(Long restaurantId);

}
