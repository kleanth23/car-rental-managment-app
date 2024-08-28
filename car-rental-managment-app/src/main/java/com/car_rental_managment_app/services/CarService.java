package com.car_rental_managment_app.services;

import com.car_rental_managment_app.entities.BranchEntity;
import com.car_rental_managment_app.entities.CarEntity;
import com.car_rental_managment_app.enums.CarStatus;
import com.car_rental_managment_app.exceptions.UserNotFoundException;
import com.car_rental_managment_app.repository.BranchRepository;
import com.car_rental_managment_app.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    @Autowired
    BranchRepository branchRepository;

    public CarEntity createCar(CarEntity car, Long branchId) {
        try {
            car.setCarStatus(CarStatus.AVAILABLE);
            Optional<BranchEntity> branchEntity = branchRepository.findById(branchId);
            if (branchEntity.isEmpty()) {
                throw new UserNotFoundException("Branch with given id does not exist");
            }
            car.setBranchEntity(branchEntity.get());
            car.setReservationEntities(car.getReservationEntities());
            return carRepository.save(car);
        } catch (Exception e) {
            throw new RuntimeException("Error creating car: " + e.getMessage(), e);
        }
    }

    public Optional<CarEntity> getCarById(Long carId) {
        try {
            return carRepository.findById(carId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving car: " + e.getMessage(), e);
        }
    }

    public CarEntity updateCar(Long carId, CarEntity car) {
        try {
            Optional<CarEntity> carEntity = carRepository.findById(carId);
            if (carEntity.isPresent()) {
                CarEntity updatedCar = getCarEntity(car, carEntity);
                return carRepository.saveAndFlush(updatedCar);
            } else {
                throw new UserNotFoundException("Car with given id does not exist");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating car: " + e.getMessage(), e);
        }
    }

    private static CarEntity getCarEntity(CarEntity car, Optional<CarEntity> carEntity) {
        CarEntity updatedCar = carEntity.get();
        updatedCar.setBrand(car.getBrand());
        updatedCar.setColour(car.getColour());
        updatedCar.setModel(car.getModel());
        updatedCar.setProductionYear(car.getProductionYear());
        updatedCar.setLicensePlate(car.getLicensePlate());
        updatedCar.setRentalPerDay(car.getRentalPerDay());
        updatedCar.setEngine(car.getEngine());
        updatedCar.setAvailable(car.isAvailable());
        return updatedCar;
    }

    public void deleteCar(Long carId) {
        try {
            carRepository.deleteById(carId);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Car with given id does not exist");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting car: " + e.getMessage(), e);
        }
    }

    public List<CarEntity> getCarsByBranchId(Long branchId) throws RuntimeException {
        try {
            return branchRepository.findById(branchId)
                    .map(BranchEntity::getCarEntities)
                    .orElseThrow(() -> new UserNotFoundException("Branch with given id does not exist"));
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving cars by branch id: " + e.getMessage(), e);
        }
    }
}
