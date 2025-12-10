package com.food_del_pltfrm.restaurant_service.controllers;

import com.food_del_pltfrm.restaurant_service.dtos.RestaurantCreateDTO;
import com.food_del_pltfrm.restaurant_service.dtos.RestaurantDTO;
import com.food_del_pltfrm.restaurant_service.dtos.RestaurantUpdateDTO;
import com.food_del_pltfrm.restaurant_service.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService service;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestaurantDTO create(@RequestBody RestaurantCreateDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestaurantDTO update(@PathVariable Long id, @RequestBody RestaurantUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public RestaurantDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<RestaurantDTO> getAll() {
        return service.getAll();
    }
}