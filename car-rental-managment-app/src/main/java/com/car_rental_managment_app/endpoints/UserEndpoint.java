package com.car_rental_managment_app.endpoints;

import com.car_rental_managment_app.entities.CarEntity;
import com.car_rental_managment_app.entities.UserEntity;
import com.car_rental_managment_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserEndpoint {
    @Autowired UserService userService;

    @PostMapping("/createUser/{branchId}")
    public ResponseEntity<UserEntity> createUser (@PathVariable Long branchId, @RequestBody UserEntity user){
        UserEntity createdUser = userService.saveUser(user, branchId);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<Optional<UserEntity>> getUserById (@PathVariable Long userId){
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
    public ResponseEntity<List<UserEntity>> getUserByBranchId(@PathVariable Long branchId){
        List<UserEntity> getUserByBranchId = userService.getUserByBranchId(branchId);
        return new ResponseEntity<>(getUserByBranchId,HttpStatus.OK);
    }
}
