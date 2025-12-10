package com.food_del_pltfrm.order_service.repositories;

import com.food_del_pltfrm.order_service.entities.Order_item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<Order_item, Long> {
}
