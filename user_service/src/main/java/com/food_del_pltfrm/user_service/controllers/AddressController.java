package com.food_del_pltfrm.user_service.controllers;


import com.food_del_pltfrm.user_service.dtos.AddressDTO;
import com.food_del_pltfrm.user_service.dtos.AddressUpdateDTO;
import com.food_del_pltfrm.user_service.dtos.UserDTO;
import com.food_del_pltfrm.user_service.services.interfaces.AddressService;
import com.food_del_pltfrm.user_service.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adresses")
@AllArgsConstructor
public class AddressController {

    private final UserService userService;
    private final AddressService addressService;


    @GetMapping("/me")
    public ResponseEntity<List<AddressDTO>> getAllAdressesForPersonalUser(Authentication authentication) {
        List<AddressDTO> dtos = addressService.getAddressesByUser(authentication.getName());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/me")
    public ResponseEntity<AddressDTO> createAddressForPersonalUser(Authentication authentication,@RequestBody AddressDTO addressDTO){
        AddressDTO dto = addressService.createAddressToUser(authentication.getName(), addressDTO);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/me/{id}")
    public ResponseEntity<AddressDTO> updateAddressForPersonalUser(Authentication authentication,@PathVariable Long id, @RequestBody AddressUpdateDTO addressUpdateDTO){
        AddressDTO dto = addressService.updateAddressToUser(authentication.getName(), id, addressUpdateDTO);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("/me/{id}")
    public ResponseEntity<Void> deleteAddressForPersonalUser(Authentication authentication,@PathVariable Long id){
        addressService.deleteAddressToUser(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AddressDTO>> getAllAdressesForCurrentUser(Authentication authentication, @PathVariable String email) {
        List<AddressDTO> dtos = addressService.getAddressesByUser(email);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AddressDTO> createAddressForCurrentUser(Authentication authentication,@PathVariable String email, @RequestBody AddressDTO addressDTO){
        AddressDTO dto = addressService.createAddressToUser(email, addressDTO);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{email}/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AddressDTO> updateAddressForCurrentUser(Authentication authentication,@PathVariable String email, @PathVariable Long id,  @RequestBody AddressUpdateDTO addressUpdateDTO){
        AddressDTO dto = addressService.updateAddressToUser(email, id, addressUpdateDTO);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("/{email}/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAddressForCurrentUser(Authentication authentication,@PathVariable String email, @PathVariable Long id){
        addressService.deleteAddressToUser(email, id);
        return ResponseEntity.noContent().build();
    }

}
