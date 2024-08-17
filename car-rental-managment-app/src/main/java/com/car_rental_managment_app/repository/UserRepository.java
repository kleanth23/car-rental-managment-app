package com.car_rental_managment_app.repository;

import com.car_rental_managment_app.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
