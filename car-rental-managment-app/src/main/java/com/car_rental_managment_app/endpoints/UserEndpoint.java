package com.car_rental_managment_app.endpoints;

import com.car_rental_managment_app.entities.UserEntity;
import com.car_rental_managment_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserEndpoint {
    @Autowired UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserEntity> createUser (@RequestBody UserEntity user){
        UserEntity createdUser = userService.saveUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
