package com.food_del_pltfrm.order_service.dtos;


import com.food_del_pltfrm.order_service.entities.Order_item;
import com.food_del_pltfrm.order_service.entities.Payment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String status;
    private Long userId;
    private LocalDateTime createdAt;
    private Long restaurantId;
    private Integer totalPrice;
    private List<OrderItemDTO> items;
    private List<PaymentDTO> payments;

}
