package com.car_rental_managment_app.services;

import com.car_rental_managment_app.entities.BranchEntity;
import com.car_rental_managment_app.entities.CarEntity;
import com.car_rental_managment_app.enums.CarStatus;
import com.car_rental_managment_app.repository.BranchRepository;
import com.car_rental_managment_app.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    @Autowired
    BranchRepository branchRepository;

    public CarEntity createCar (CarEntity car, Long branchId){
        car.setCarStatus(CarStatus.AVAILABLE);
        Optional<BranchEntity> branchEntity = branchRepository.findById(branchId);
        if (branchEntity.isEmpty()){
            throw new RuntimeException("Branch with given id does not exist");
        }
        car.setBranchEntity(branchEntity.get());
        car.setReservationEntities(car.getReservationEntities());
        return carRepository.save(car);
    }

    public Optional<CarEntity> getCarById (Long carId){
        return carRepository.findById(carId);
    }

    public CarEntity updateCar (Long carId, CarEntity car){
        Optional<CarEntity> carEntity = carRepository.findById(carId);

        if (carEntity.isPresent()){
            CarEntity updatedCar = carEntity.get();
            updatedCar.setBrand(car.getBrand());
            updatedCar.setColour(car.getColour());
            updatedCar.setModel(car.getModel());
            updatedCar.setProductionYear(car.getProductionYear());
            updatedCar.setLicensePlate(car.getLicensePlate());
            updatedCar.setRentalPerDay(car.getRentalPerDay());
            updatedCar.setEngine(car.getEngine());

            return carRepository.saveAndFlush(updatedCar);
        }
        return null;
    }

    public void deleteCar(Long carId){
        carRepository.deleteById(carId);
    }

    public List<CarEntity> getCarsByBranchId (Long branchId){
        return branchRepository.findById(branchId)
                .map(BranchEntity::getCarEntities).orElseThrow(() -> new EntityNotFoundException("Car not found"));
    }
}
