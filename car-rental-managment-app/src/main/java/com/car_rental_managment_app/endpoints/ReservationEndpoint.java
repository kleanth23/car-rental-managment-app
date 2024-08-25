package com.car_rental_managment_app.endpoints;

import com.car_rental_managment_app.entities.ReservationEntity;
import com.car_rental_managment_app.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationEndpoint {
    @Autowired
    ReservationService reservationService;

    @PostMapping("/createReservation/{userId}/{carId}")
    public ResponseEntity<ReservationEntity> createReservation (@RequestBody ReservationEntity reservationEntity,
                                                                @PathVariable Long userId, @PathVariable Long carId ){
        ReservationEntity reservation = reservationService.createReservation(reservationEntity,carId,userId);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }
}
