package com.car_rental_managment_app.endpoints;

import com.car_rental_managment_app.entities.BranchEntity;
import com.car_rental_managment_app.entities.CarEntity;
import com.car_rental_managment_app.repository.CarRepository;
import com.car_rental_managment_app.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarEndpoint {
    @Autowired
    CarService carService;

    @PostMapping("/createCar/{branchId}")
    public ResponseEntity<CarEntity> createCar (@PathVariable Long branchId, @RequestBody CarEntity car){
        CarEntity createdCar = carService.createCar(car, branchId);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @GetMapping("/getCarById/{carId}")
    public ResponseEntity<Optional<CarEntity>> getCarById (@PathVariable Long carId){
        Optional<CarEntity> returnedCar = carService.getCarById(carId);
        return new ResponseEntity<>(returnedCar, HttpStatus.OK);
    }

    @PostMapping("/updateCar/{carId}")
    public ResponseEntity<CarEntity> updateCar (@PathVariable Long carId, @RequestBody CarEntity car){
        CarEntity updateCar = carService.updateCar(carId,car);
        return new ResponseEntity<>(updateCar, HttpStatus.OK);
    }
    @DeleteMapping("/deleteCar/{carId}")
    public ResponseEntity<Void> deleteCar (@PathVariable Long carId){
        carService.deleteCar(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getCarsByBranchId/{branchId}")
    public ResponseEntity<List<CarEntity>> getCarByBranchId(@PathVariable Long branchId){
        List<CarEntity> getCarByBranchId = carService.getCarsByBranchId(branchId);
        return new ResponseEntity<>(getCarByBranchId,HttpStatus.OK);
    }
}
