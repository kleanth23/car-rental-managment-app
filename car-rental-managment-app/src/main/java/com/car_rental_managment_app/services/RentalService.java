package com.car_rental_managment_app.services;

import com.car_rental_managment_app.entities.RentalEntity;
import com.car_rental_managment_app.exceptions.RentalNotFoundException;
import com.car_rental_managment_app.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
    @Autowired
    RentalRepository rentalRepository;

    public RentalEntity createRentalCompany(RentalEntity rentalEntity) {
        try {
            return rentalRepository.save(rentalEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating rental company: " + e.getMessage(), e);
        }
    }

    public Optional<RentalEntity> getRentalById(Long rentalId) {
        try {
            return rentalRepository.findById(rentalId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving rental company: " + e.getMessage(), e);
        }
    }

    public RentalEntity updateRental(RentalEntity rental, Long rentalId) {
        try {
            Optional<RentalEntity> rentalEntity = rentalRepository.findById(rentalId);
            if (rentalEntity.isPresent()) {
                RentalEntity updatedRental = rentalEntity.get();
                updatedRental.setName(rental.getName());
                updatedRental.setEmail(rental.getEmail());
                updatedRental.setOwner(rental.getOwner());
                updatedRental.setBranchEntities(rental.getBranchEntities());
                return rentalRepository.saveAndFlush(updatedRental);
            } else {
                throw new RentalNotFoundException("Rental company with given id does not exist");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating rental company: " + e.getMessage(), e);
        }
    }

    public void deleteRental(Long rentalId) {
        try {
            rentalRepository.deleteById(rentalId);
        } catch (EmptyResultDataAccessException e) {
            throw new RentalNotFoundException("Rental company with given id does not exist");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting rental company: " + e.getMessage(), e);
        }
    }

    public List<RentalEntity> getAllRentals() {
        try {
            return rentalRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all rental companies: " + e.getMessage(), e);
        }
    }
}
