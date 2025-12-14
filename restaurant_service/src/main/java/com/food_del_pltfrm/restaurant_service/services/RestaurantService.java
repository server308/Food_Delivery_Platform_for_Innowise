package com.food_del_pltfrm.restaurant_service.services;

import com.food_del_pltfrm.restaurant_service.dtos.RestaurantCreateDTO;
import com.food_del_pltfrm.restaurant_service.dtos.RestaurantDTO;
import com.food_del_pltfrm.restaurant_service.dtos.RestaurantUpdateDTO;
import com.food_del_pltfrm.restaurant_service.entities.Restaurant;
import com.food_del_pltfrm.restaurant_service.mappers.RestaurantMapper;
import com.food_del_pltfrm.restaurant_service.repositories.RestaurantRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;


    @Transactional
    public RestaurantDTO create(RestaurantCreateDTO dto) {
        Restaurant entity = restaurantMapper.toEntity(dto);
        Restaurant savedRestaurant = restaurantRepository.save(entity);
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(savedRestaurant);

        return restaurantDTO;
    }


    @Transactional
    public RestaurantDTO update(Long id, RestaurantUpdateDTO dto) {
        Restaurant rest = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        rest.setName(dto.getName());
        rest.setCuisine(dto.getCuisine());
        rest.setAddress(dto.getAddress());

        Restaurant savedRestaurant = restaurantRepository.save(rest);
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(savedRestaurant);

        return restaurantDTO;
    }

    public void delete(Long id) {

        restaurantRepository.deleteById(id);

    }

    public RestaurantDTO get(Long id) {
        return restaurantMapper.toDto(
                restaurantRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Restaurant not found"))
        );
    }

    public List<RestaurantDTO> getAll() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
    }
}