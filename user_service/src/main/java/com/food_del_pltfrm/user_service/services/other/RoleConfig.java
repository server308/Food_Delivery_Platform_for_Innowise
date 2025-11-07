package com.food_del_pltfrm.user_service.services.other;

import com.food_del_pltfrm.user_service.entities.Role;
import com.food_del_pltfrm.user_service.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleConfig {

    private final RoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void CreateAdminAndUserRole(){
        if (roleRepository.findRoleByName("ADMIN") ==null){
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);
        }
        if (roleRepository.findRoleByName("USER") ==null){
            Role role = new Role();
            role.setName("USER");
            roleRepository.save(role);
        }
    }
}
