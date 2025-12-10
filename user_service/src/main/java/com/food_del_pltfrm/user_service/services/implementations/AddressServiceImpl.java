package com.food_del_pltfrm.user_service.services.implementations;

import com.food_del_pltfrm.user_service.dtos.AddressDTO;
import com.food_del_pltfrm.user_service.dtos.AddressUpdateDTO;
import com.food_del_pltfrm.user_service.entities.Address;
import com.food_del_pltfrm.user_service.entities.User;
import com.food_del_pltfrm.user_service.mappers.AddressMapper;
import com.food_del_pltfrm.user_service.repositories.AddressRepository;
import com.food_del_pltfrm.user_service.repositories.UserRepository;
import com.food_del_pltfrm.user_service.services.interfaces.AddressService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final AmqpTemplate amqpTemplate;

    @Override
    @Transactional
    public AddressDTO createAddressToUser(String email, AddressDTO dto) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        Address address = addressMapper.toAddress(dto);
        address.setUser(user);
        addressRepository.save(address);
        return addressMapper.toAddressDto(address);
    }


    @Override
    @Transactional(readOnly = true)
    public List<AddressDTO> getAddressesByUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        List<Address> addresses = addressRepository.findAllByUser(user);

            List<AddressDTO> addressDTOList = addressMapper.toAddressDtoList(addresses);

        return addressDTOList;
    }

    @Override
    @Transactional
    public AddressDTO updateAddressToUser(String email, Long address_id, AddressUpdateDTO dto) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        Address address = addressRepository.findByUserAndId(user, address_id).orElseThrow(()-> new RuntimeException("Address not found"));
        if (dto.getStreet() != null && !dto.getStreet().isEmpty()) {
            address.setStreet(dto.getStreet());
        }
        if (dto.getCity() != null && !dto.getCity().isEmpty()) {
            address.setCity(dto.getCity());
        }
        if (dto.getZip_code() != null && !dto.getZip_code().isEmpty()) {
            address.setZip_code(dto.getZip_code());
        }
        if (dto.getState() != null && !dto.getState().isEmpty()) {
            address.setState(dto.getState());
        }
        if (dto.getCountry() != null && !dto.getCountry().isEmpty()) {
            address.setCountry(dto.getCountry());
        }
        Address updatedAddress = addressRepository.save(address);

        return addressMapper.toAddressDto(updatedAddress);
    }

    @Override
    @Transactional
    public void deleteAddressToUser(String email, Long address_id) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        Address address = addressRepository.findByUserAndId(user, address_id).orElseThrow(()-> new RuntimeException("Address not found"));
        addressRepository.delete(address);
    }



    //Test method
    public void sendMessage(){

    }
}
