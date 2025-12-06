package com.food_del_pltfrm.user_service.mappers;

import com.food_del_pltfrm.user_service.dtos.*;
import com.food_del_pltfrm.user_service.entities.User;
import com.food_del_pltfrm.user_service.entities.Address;
import com.food_del_pltfrm.user_service.entities.Role;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDto(User user);

    // UserUpdateDTO -> User (для обновления)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    void updateUserFromDto(UserUpdateDTO userUpdateDto, @MappingTarget User user);

    // List mappings
    List<UserDTO> toUserDtoList(List<User> users);

    // Кастомный метод для преобразования ролей
    default List<String> mapRoles(List<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    // Обработка после маппинга для ролей
    @AfterMapping
    default void afterUserToDtoMapping(User user, @MappingTarget UserDTO userDTO) {
        if (user.getRoles() != null) {
            userDTO.setRoles(mapRoles(user.getRoles()));
        }
    }

}