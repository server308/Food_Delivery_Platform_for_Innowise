package com.food_del_pltfrm.user_service.repositories;

import com.food_del_pltfrm.user_service.entities.Address;
import com.food_del_pltfrm.user_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByUserAndId(User user, Long addressId);
    List<Address> findAllByUser(User user);

}
