package com.food_del_pltfrm.order_service.repositories;

import com.food_del_pltfrm.order_service.entities.Order_item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<Order_item, Long> {
    Optional<Order_item> findByOrderIdAndId(Long orderId, Long id);
}
