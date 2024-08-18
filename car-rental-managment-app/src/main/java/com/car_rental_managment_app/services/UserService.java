package com.car_rental_managment_app.services;

import com.car_rental_managment_app.entities.UserEntity;
import com.car_rental_managment_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired UserRepository userRepository;

    public UserEntity saveUser (UserEntity user){
        return userRepository.save(user);
    }

    public Optional<UserEntity> findById(Long userId){
        return userRepository.findById(userId);
    }

    public UserEntity updateUser(UserEntity user, Long userId){
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity.isPresent()){
            UserEntity updatedUser = userEntity.get();
            updatedUser.setName(user.getName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setAge(user.getAge());
            updatedUser.setRole(user.getRole());
        }
        return null;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
