package com.car_rental_managment_app.endpoints;

import com.car_rental_managment_app.entities.ReservationEntity;
import com.car_rental_managment_app.services.ReservationService;
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
@RequestMapping("/api/reservations")
public class ReservationEndpoint {
    @Autowired
    ReservationService reservationService;

    @PostMapping("/createReservation/{userId}/{carId}")
    public ResponseEntity<ReservationEntity> createReservation(@RequestBody ReservationEntity reservationEntity,
                                                               @PathVariable Long userId, @PathVariable Long carId) {
        ReservationEntity reservation = reservationService.createReservation(reservationEntity, carId, userId);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }


    @GetMapping("/getReservation/{reservationId}")
    public ResponseEntity<Optional<ReservationEntity>> getReservationsById(@PathVariable Long reservationId){
        Optional<ReservationEntity> getReservation= reservationService.getReservation(reservationId);
        return new ResponseEntity<>(getReservation,HttpStatus.OK);
    }

    @PutMapping("/updateReservation/{reservationId}")
    public ResponseEntity<ReservationEntity> updateReservations(@RequestBody ReservationEntity reservation, @PathVariable Long reservationId){
        ReservationEntity updateReservation = reservationService.updateReservation(reservation,reservationId);
        return new ResponseEntity<>(updateReservation,HttpStatus.OK);
    }

    @DeleteMapping("/deleteReservation/{reservationId}")
    public ResponseEntity<ReservationEntity> deleteReservations(@PathVariable Long reservationId){
        reservationService.deleteReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAllReservations")
    public ResponseEntity<List<ReservationEntity>> getAllReservations(){
        List<ReservationEntity> getAllReservation = reservationService.getAllReservation();
        return new ResponseEntity<>(getAllReservation, HttpStatus.OK);
    }
}
