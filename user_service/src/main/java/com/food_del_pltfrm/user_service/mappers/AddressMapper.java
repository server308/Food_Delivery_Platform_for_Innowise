package com.food_del_pltfrm.user_service.mappers;


import com.food_del_pltfrm.user_service.dtos.AddressDTO;
import com.food_del_pltfrm.user_service.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "user_id", expression = "java(address.getUser() != null ? address.getUser().getId() : null)")
    AddressDTO toAddressDto(Address address);

    // AddressDTO -> Address
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id",  expression = "java(address.getId() != null ? address.getId() : null)")
    Address toAddress(AddressDTO addressDTO);


}