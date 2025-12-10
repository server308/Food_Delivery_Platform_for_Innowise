package com.food_del_pltfrm.order_service.mappers;

import com.food_del_pltfrm.order_service.dtos.CreatePaymentDTO;
import com.food_del_pltfrm.order_service.dtos.PaymentDTO;
import com.food_del_pltfrm.order_service.dtos.UpdatePaymentDTO;
import com.food_del_pltfrm.order_service.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    Payment toEntity(CreatePaymentDTO dto);

    @Mapping(target = "order", ignore = true)
    Payment toEntity(UpdatePaymentDTO dto);

    PaymentDTO toDTO(Payment entity);
}