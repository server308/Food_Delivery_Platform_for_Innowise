package com.food_del_pltfrm.restaurant_service.repositories;

import com.food_del_pltfrm.restaurant_service.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
