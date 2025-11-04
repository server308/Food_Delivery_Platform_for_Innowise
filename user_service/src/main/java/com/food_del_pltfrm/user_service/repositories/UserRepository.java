package com.food_del_pltfrm.user_service.repositories;


import com.food_del_pltfrm.user_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFullName(String fullName);
    Optional<User> findByEmail(String email);
    boolean existsByFullName(String fullName);
    boolean existsByEmail(String email);
}
