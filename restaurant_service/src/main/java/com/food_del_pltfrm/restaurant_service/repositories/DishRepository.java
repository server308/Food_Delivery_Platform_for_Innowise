package com.food_del_pltfrm.restaurant_service.repositories;

import com.food_del_pltfrm.restaurant_service.entities.Dish;
import com.food_del_pltfrm.restaurant_service.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByRestaurantId(Long restaurantId);

    Optional<Dish> findDishByRestaurantAndId(Restaurant restaurant, Long id);
}
