package com.car_rental_managment_app.endpoints;

import com.car_rental_managment_app.entities.UserEntity;
import com.car_rental_managment_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserEndpoint {
    @Autowired
    UserService userService;

    @PostMapping("/createUser/{branchId}")
    public ResponseEntity<UserEntity> createUser(@PathVariable Long branchId, @RequestBody UserEntity user) {
        UserEntity createdUser = userService.saveUser(user, branchId);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/registerUser")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user) {
        userService.registerUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<Optional<UserEntity>> loginUser(@Param(value = "email") String email, @Param(value = "password") String password) {
        Optional<UserEntity> optionalUserEntity = userService.loginUser(email, password);
        return new ResponseEntity<>(optionalUserEntity, HttpStatus.OK);
    }


    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<Optional<UserEntity>> getUserById(@PathVariable Long userId) {
        Optional<UserEntity> returnedUser = userService.findById(userId);
        return new ResponseEntity<>(returnedUser, HttpStatus.OK);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user, @PathVariable Long userId) {
        UserEntity updatedUser = userService.updateUser(user, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getUserByBranchId/{branchId}")
    public ResponseEntity<List<UserEntity>> getUserByBranchId(@PathVariable Long branchId) {
        List<UserEntity> getUserByBranchId = userService.getUserByBranchId(branchId);
        return new ResponseEntity<>(getUserByBranchId, HttpStatus.OK);
    }
}
