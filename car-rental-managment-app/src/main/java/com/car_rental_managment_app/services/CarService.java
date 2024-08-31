package com.car_rental_managment_app.services;

import com.car_rental_managment_app.entities.BranchEntity;
import com.car_rental_managment_app.entities.CarEntity;
import com.car_rental_managment_app.entities.ImageEntity;
import com.car_rental_managment_app.enums.CarStatus;
import com.car_rental_managment_app.exceptions.UserNotFoundException;
import com.car_rental_managment_app.repository.BranchRepository;
import com.car_rental_managment_app.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
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

    @Autowired ImageService imageService;

    public CarEntity createCar(CarEntity car, Long branchId) {
        try {
            car.setStatus(CarStatus.AVAILABLE);
            Optional<BranchEntity> branchEntity = branchRepository.findById(branchId);
            if (branchEntity.isEmpty()) {
                throw new UserNotFoundException("Branch with given id does not exist");
            }
            car.setBranchEntity(branchEntity.get());
            car.setReservationEntities(car.getReservationEntities());
            setCarDocumentImage(car);
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

    public CarEntity updateCar(CarEntity car, Long carId) {
        if (!carRepository.existsById(carId)) {
            throw new EntityNotFoundException("You are not able to update  this car because it does not exist");
        }
        Optional<CarEntity> carEntity = carRepository.findById(carId);

        carEntity.get().setModel(car.getModel());
        carEntity.get().setColour(car.getColour());
        carEntity.get().setBrand(car.getBrand());
        carEntity.get().setRentalPerDay(car.getRentalPerDay());
        carEntity.get().setProductionYear(car.getProductionYear());
        carEntity.get().setStatus(car.getStatus());

        return carRepository.save(carEntity.get());
    }

    public Optional<CarEntity> getCar(Long carId) {
        return Optional.of(carRepository.findById(carId)).orElseThrow(() -> new EntityNotFoundException("Car not found with this :" + carId));

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

    public List<CarEntity> getCarsByBranchId(Long branchId) {
        return branchRepository.findById(branchId).map(BranchEntity::getCarEntities)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
    }


    private void setCarDocumentImage(CarEntity car) {
        ImageEntity image = car.getImage();
        if (image != null) {
            image.setUrl(imageService.generateImageAbsolutePath());
        }
    }

}
