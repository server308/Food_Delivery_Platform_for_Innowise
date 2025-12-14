package com.food_del_pltfrm.order_service.mappers;


import com.food_del_pltfrm.order_service.dtos.*;
import com.food_del_pltfrm.order_service.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(
        componentModel = "spring",
        uses = {OrderItemMapper.class, PaymentMapper.class}
)
public interface OrderMapper {

    OrderDTO toDTO(Order order);

    @Mapping(target = "order_items", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "restaurantId", source = "restaurantId")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Order toEntity(CreateOrderDTO orderDTO);


}