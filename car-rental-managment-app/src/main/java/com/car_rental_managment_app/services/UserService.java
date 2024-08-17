package com.car_rental_managment_app.services;

import com.car_rental_managment_app.entities.UserEntity;
import com.car_rental_managment_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired UserRepository userRepository;

    public UserEntity saveUser (UserEntity user){
        UserEntity createdUser = userRepository.save(user);
        return createdUser;
    }

}
