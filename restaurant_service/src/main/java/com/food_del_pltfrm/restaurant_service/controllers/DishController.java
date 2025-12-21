package com.food_del_pltfrm.restaurant_service.controllers;

import com.food_del_pltfrm.restaurant_service.dtos.*;
import com.food_del_pltfrm.restaurant_service.services.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @PostMapping("/{restaurantId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DishDTO> create(@RequestBody DishCreateDTO dto, @PathVariable Long restaurantId) {
        return ResponseEntity.ok(dishService.create(restaurantId, dto));
    }

    @PutMapping("/{restaurantId}/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DishDTO> update(@PathVariable Long id, @PathVariable Long restaurantId, @RequestBody DishUpdateDTO dto) {
        return ResponseEntity.ok(dishService.update(restaurantId, id, dto));
    }

    @DeleteMapping("/{restaurantId}/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long restaurantId, @PathVariable Long id) {
        dishService.delete(restaurantId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(dishService.get(id));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<DishDTO>> getAllByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(dishService.getAllByRestaurant(restaurantId));
    }
}
