package com.food_del_pltfrm.user_service.services.interfaces;

import com.food_del_pltfrm.user_service.dtos.AddressDTO;
import com.food_del_pltfrm.user_service.dtos.AddressUpdateDTO;

import java.util.List;

public interface AddressService {

    AddressDTO createAddressToUser(String email, AddressDTO dto);
    AddressDTO updateAddressToUser(String email, Long address_id, AddressUpdateDTO dto);
    void deleteAddressToUser(String email, Long address_id);

    List<AddressDTO> getAddressesByUser(String email);


}
