package com.car_rental_managment_app.services;

import com.car_rental_managment_app.entities.BranchEntity;
import com.car_rental_managment_app.entities.RentalEntity;
import com.car_rental_managment_app.repository.BranchRepository;
import com.car_rental_managment_app.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    @Autowired
    BranchRepository branchRepository;

    public BranchEntity createBranch (BranchEntity branchEntity){
        return branchRepository.save(branchEntity);
    }

    public Optional<BranchEntity> getBranchById (Long branchId){
        return branchRepository.findById(branchId);
    }

    public BranchEntity updateBranch (Long branchId,BranchEntity branch){
        Optional<BranchEntity> branchEntity = branchRepository.findById(branchId);

        if (branchEntity.isPresent()){
            BranchEntity updatedBranch = branchEntity.get();
            updatedBranch.setAddress(branch.getAddress());
            updatedBranch.setCity(branch.getCity());
            updatedBranch.setRevenue(branch.getRevenue());
            updatedBranch.setRentalEntity(branch.getRentalEntity());
            updatedBranch.setCarEntities(branch.getCarEntities());
            return branchRepository.saveAndFlush(updatedBranch);
        }
        return null;
    }
    public void deleteBranch (Long branchId){
        branchRepository.deleteById(branchId);
    }

    @Autowired
    RentalRepository rentalRepository;

    public List<BranchEntity> getBranchByRentalId (Long rentalId){
        return rentalRepository.findById(rentalId).map(RentalEntity::getBranchEntities).orElseThrow(() -> new EntityNotFoundException("Branch not found"));
    }
}
