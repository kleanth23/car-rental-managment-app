package com.car_rental_managment_app.endpoints;

import com.car_rental_managment_app.entities.CarEntity;
import com.car_rental_managment_app.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/cars")
public class CarEndpoint {
    @Autowired
    CarService carService;

    @PostMapping("/createCar/{branchId}")
    public ResponseEntity<CarEntity> createCar(@PathVariable Long branchId, @RequestBody CarEntity car) {
        CarEntity createdCar = carService.createCar(car, branchId);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @GetMapping("/getCarById/{carId}")
    public ResponseEntity<Optional<CarEntity>> getCarById(@PathVariable Long carId) {
        Optional<CarEntity> returnedCar = carService.getCar(carId);
        return new ResponseEntity<>(returnedCar, HttpStatus.OK);
    }

    @PutMapping("/updateCar/{carId}")
    public ResponseEntity<CarEntity> updateCar(@PathVariable Long carId, @RequestBody CarEntity car) {
        CarEntity updateCar = carService.updateCar(car, carId);
        return new ResponseEntity<>(updateCar, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCar/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getCarsByBranchId/{branchId}")
    public ResponseEntity<List<CarEntity>> getCarByBranchId(@PathVariable Long branchId) {
        List<CarEntity> getCarByBranchId = carService.getCarsByBranchId(branchId);
        return new ResponseEntity<>(getCarByBranchId, HttpStatus.OK);
    }
}
