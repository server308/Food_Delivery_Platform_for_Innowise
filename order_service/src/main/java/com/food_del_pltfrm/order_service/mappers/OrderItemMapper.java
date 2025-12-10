package com.food_del_pltfrm.order_service.mappers;

import com.food_del_pltfrm.order_service.dtos.CreateOrderItemDTO;
import com.food_del_pltfrm.order_service.dtos.OrderItemDTO;
import com.food_del_pltfrm.order_service.dtos.UpdateOrderItemDTO;
import com.food_del_pltfrm.order_service.entities.Order_item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    Order_item toEntity(CreateOrderItemDTO dto);

    @Mapping(target = "order", ignore = true)
    Order_item toEntity(UpdateOrderItemDTO dto);

    OrderItemDTO toDTO(Order_item entity);
}