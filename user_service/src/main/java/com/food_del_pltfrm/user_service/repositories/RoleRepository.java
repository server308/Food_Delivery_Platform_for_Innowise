package com.food_del_pltfrm.user_service.repositories;


import com.food_del_pltfrm.user_service.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
