package com.car_rental_managment_app.services;

import com.car_rental_managment_app.entities.CarEntity;
import com.car_rental_managment_app.entities.ReservationEntity;
import com.car_rental_managment_app.entities.UserEntity;
import com.car_rental_managment_app.enums.CarStatus;
import com.car_rental_managment_app.exceptions.ReservationNotFoundException;
import com.car_rental_managment_app.exceptions.UserNotFoundException;
import com.car_rental_managment_app.repository.CarRepository;
import com.car_rental_managment_app.repository.ReservationRepository;
import com.car_rental_managment_app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarService carService;

    @Autowired
    UserRepository userRepository;

    public ReservationEntity createReservation(ReservationEntity reservation, Long carId, Long userId) {
        try {
            Optional<CarEntity> carEntityOptional = carRepository.findById(carId);
            if (carEntityOptional.isEmpty()) {
                throw new EntityNotFoundException("Car with provided ID does not exist");

            }

            Optional<UserEntity> userEntity = userRepository.findById(userId);
            if (userEntity.isEmpty()) {
                throw new UserNotFoundException("User with provided ID does not exist");
            }

            CarEntity carEntity = carEntityOptional.get();
            if (carEntity.getCarStatus().equals(CarStatus.AVAILABLE)) {
                carEntity.setCarStatus(CarStatus.BOOKED); // Update the status of the car
                carEntity.setAvailable(false);
                reservation.setReservedBy(userEntity.get().getName());
                reservation.setCarEntity(carEntity);
                reservation.setUserEntity(userEntity.get());
                reservation.setBookingDate(new Date()); // Assuming this sets the current date as booking date
                Integer daysOfBooking = (int) ((reservation.getEndDate().getTime() - reservation.getStartDate().getTime()) / (1000 * 60 * 60 * 24));
                Double overallPrice = carEntity.getRentalPerDay() * daysOfBooking;
                reservation.setTotalAmount(overallPrice);

                carRepository.save(carEntity);
                return reservationRepository.save(reservation);
            } else {
                throw new RuntimeException("This car is unavailable");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating reservation: " + e.getMessage(), e);
        }
    }

    public Optional<ReservationEntity> getReservation(Long reservationId) {
        return Optional.ofNullable(reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with this id:" + reservationId)));
    }

    public ReservationEntity updateReservation(ReservationEntity reservation, Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new ReservationNotFoundException("You can not update  this reservation because it does not exist");
        }
        Optional<ReservationEntity> reservationEntity = reservationRepository.findById(reservationId);
        reservationEntity.get().setStartDate(reservation.getStartDate());
        reservationEntity.get().setBookingDate(reservation.getBookingDate());
        reservationEntity.get().setEndDate(reservation.getEndDate());
        return reservationRepository.save(reservationEntity.get());
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public List<ReservationEntity> getAllReservation() {
        return reservationRepository.findAll();
    }

}







