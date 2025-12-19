package com.food_del_pltfrm.order_service.repositories;

import com.food_del_pltfrm.order_service.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findAllByUserId(Long userId);
}
