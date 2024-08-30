package com.car_rental_managment_app.endpoints;

import com.car_rental_managment_app.entities.BranchEntity;
import com.car_rental_managment_app.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/branches")
public class BranchEndpoint {
    @Autowired
    BranchService branchService;
    @PostMapping("/createBranch/{rentalId}")
    public ResponseEntity<BranchEntity> createBranches(@RequestBody BranchEntity branch,@PathVariable Long rentalId) {
        BranchEntity createBranch = branchService.createBranch(branch,rentalId);
        return new ResponseEntity<>(createBranch, HttpStatus.CREATED);
    }

    @GetMapping("/getBranchById/{branchId}")
    public ResponseEntity<Optional<BranchEntity>> getBranchById(@PathVariable Long branchId) {
        Optional<BranchEntity> returnedBranch = branchService.getBranchById(branchId);
        return new ResponseEntity<>(returnedBranch, HttpStatus.OK);
    }

    @PostMapping("/updateBranch/{branchId}")
    public ResponseEntity<BranchEntity> updateBranch(@PathVariable Long branchId, @RequestBody BranchEntity branch) {
        BranchEntity updatedBranch = branchService.updateBranch(branchId, branch);
        return new ResponseEntity<>(updatedBranch, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBranch/{branchId}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long branchId) {
        branchService.deleteBranch(branchId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getBranchByRentalId/{rentalId}")
    public ResponseEntity<List<BranchEntity>> getBranchByRentalId(@PathVariable Long rentalId) {
        List<BranchEntity> getBranchByRentalId = branchService.getBranchByRentalId(rentalId);
        return new ResponseEntity<>(getBranchByRentalId, HttpStatus.OK);
    }
}
