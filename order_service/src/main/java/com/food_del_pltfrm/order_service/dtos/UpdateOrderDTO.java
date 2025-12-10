package com.food_del_pltfrm.order_service.dtos;


import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderDTO {
    private Long id;
    private String status;
    private Long restaurantId;
    private Integer totalPrice;
    private List<UpdateOrderItemDTO> items;
    private List<UpdatePaymentDTO> payments;
}
