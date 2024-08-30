package com.car_rental_managment_app.services;

import com.car_rental_managment_app.entities.BranchEntity;
import com.car_rental_managment_app.entities.RentalEntity;
import com.car_rental_managment_app.exceptions.BranchNotFoundException;
import com.car_rental_managment_app.repository.BranchRepository;
import com.car_rental_managment_app.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    @Autowired
    BranchRepository branchRepository;

    @Autowired
    RentalRepository rentalRepository;

    public BranchEntity createBranch(BranchEntity branch, Long rentalId) {
        RentalEntity rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with ID: " + rentalId));

        branch.setRentalEntity(rental);
        rental.getBranchEntities().add(branch);
        return branchRepository.save(branch);
    }

    public Optional<BranchEntity> getBranchById(Long branchId) {
        try {
            return branchRepository.findById(branchId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving branch: " + e.getMessage(), e);
        }
    }

    public BranchEntity updateBranch(Long branchId, BranchEntity branch) {
        try {
            Optional<BranchEntity> branchEntity = branchRepository.findById(branchId);
            if (branchEntity.isPresent()) {
                BranchEntity updatedBranch = branchEntity.get();
                updatedBranch.setAddress(branch.getAddress());
                updatedBranch.setCity(branch.getCity());
                updatedBranch.setRevenue(branch.getRevenue());
                updatedBranch.setRentalEntity(branch.getRentalEntity());
                updatedBranch.setCarEntities(branch.getCarEntities());
                return branchRepository.saveAndFlush(updatedBranch);
            } else {
                throw new BranchNotFoundException("Branch with given id does not exist");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating branch: " + e.getMessage(), e);
        }
    }

    public void deleteBranch(Long branchId) {
        try {
            branchRepository.deleteById(branchId);
        } catch (EmptyResultDataAccessException e) {
            throw new BranchNotFoundException("Branch with given id does not exist");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting branch: " + e.getMessage(), e);
        }
    }

    public List<BranchEntity> getBranchByRentalId(Long rentalId) {
        try {
            return rentalRepository.findById(rentalId)
                    .map(RentalEntity::getBranchEntities)
                    .orElseThrow(() -> new BranchNotFoundException("Branch not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving branches by rental id: " + e.getMessage(), e);
        }
    }
}
