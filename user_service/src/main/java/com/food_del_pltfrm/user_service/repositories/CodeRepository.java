package com.food_del_pltfrm.user_service.repositories;

import com.food_del_pltfrm.user_service.entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<VerificationCode, Long> {
}
