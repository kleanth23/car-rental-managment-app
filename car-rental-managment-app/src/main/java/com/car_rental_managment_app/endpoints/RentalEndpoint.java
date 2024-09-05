package com.car_rental_managment_app.endpoints;

import com.car_rental_managment_app.entities.RentalEntity;
import com.car_rental_managment_app.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
public class RentalEndpoint {
    @Autowired
    RentalService rentalService;

    @PostMapping("/createRental")
    public ResponseEntity<RentalEntity> createRental(@RequestBody RentalEntity rental) {
        RentalEntity createdRental = rentalService.createRentalCompany(rental);
        return new ResponseEntity<>(createdRental, HttpStatus.CREATED);
    }

    @GetMapping("/getRentalById/{rentalId}")
    public ResponseEntity<Optional<RentalEntity>> getRentalById(@PathVariable Long rentalId) {
        Optional<RentalEntity> returnedRental = rentalService.getRentalById(rentalId);
        return new ResponseEntity<>(returnedRental, HttpStatus.OK);
    }

    @PatchMapping("/updateRental/{rentalId}")
    public ResponseEntity<RentalEntity> updateRental(@RequestBody RentalEntity rental, @PathVariable Long rentalId) {
        RentalEntity updatedRental = rentalService.updateRental(rental, rentalId);
        return new ResponseEntity<>(updatedRental, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRental/{rentalId}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long rentalId) {
        rentalService.deleteRental(rentalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAllRentals")
    public ResponseEntity<List<RentalEntity>> getAllRentals() {
        List<RentalEntity> getAllRentals = rentalService.getAllRentals();
        return new ResponseEntity<>(getAllRentals, HttpStatus.OK);
    }
}
