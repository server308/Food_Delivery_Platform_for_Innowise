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
    @Mapping(target = "city", source = "city")
    AddressDTO toAddressDto(Address address);

    // AddressDTO -> Address

    @Mapping(target = "city", source = "city")
    @Mapping(target = "user", ignore = true)
    Address toAddress(AddressDTO addressDTO);

    List<AddressDTO> toAddressDtoList(List<Address> addresses);


}