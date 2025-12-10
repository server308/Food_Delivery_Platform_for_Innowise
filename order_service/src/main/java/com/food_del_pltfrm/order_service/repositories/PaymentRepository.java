package com.food_del_pltfrm.order_service.repositories;

import com.food_del_pltfrm.order_service.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
