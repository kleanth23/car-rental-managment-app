package com.car_rental_managment_app.services;

import com.car_rental_managment_app.entities.BranchEntity;
import com.car_rental_managment_app.entities.RentalEntity;
import com.car_rental_managment_app.entities.UserEntity;
import com.car_rental_managment_app.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
    @Autowired
    RentalRepository rentalRepository;

    public RentalEntity createRentalCompany (RentalEntity rentalEntity){
        return rentalRepository.save(rentalEntity);
    }

    public Optional<RentalEntity> getRentalById (Long rentalId){
        return rentalRepository.findById(rentalId);
    }

    public RentalEntity updateRental (RentalEntity rental, Long rentalId){
        Optional<RentalEntity> rentalEntity = rentalRepository.findById(rentalId);

        if (rentalEntity.isPresent()){
            RentalEntity updatedRental = rentalEntity.get();
            updatedRental.setName(rental.getName());
            updatedRental.setEmail(rental.getEmail());
            updatedRental.setOwner(rental.getOwner());
            updatedRental.setBranchEntities(rental.getBranchEntities());
            return rentalRepository.saveAndFlush(updatedRental);
        }
        return null;
    }

    public void deleteRental (Long rentalId){
        rentalRepository.deleteById(rentalId);
    }

    public List<RentalEntity> getAllRentals(){
        return rentalRepository.findAll();
    }
}
