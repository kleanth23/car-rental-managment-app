package com.car_rental_managment_app.services;

import com.car_rental_managment_app.entities.BranchEntity;
import com.car_rental_managment_app.entities.CarEntity;
import com.car_rental_managment_app.entities.UserEntity;
import com.car_rental_managment_app.repository.BranchRepository;
import com.car_rental_managment_app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired UserRepository userRepository;

    public UserEntity saveUser (UserEntity user, Long branchId){
        Optional<BranchEntity> branchEntity = branchRepository.findById(branchId);
        if (branchEntity.isEmpty()){
            throw new RuntimeException("Branch with given id does not exist");
        }
        user.setBranchEntity(branchEntity.get());
        return userRepository.save(user);
    }

    public Optional<UserEntity> findById(Long userId){
        return userRepository.findById(userId);
    }

    @Transactional
    public UserEntity updateUser(UserEntity user, Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity.isPresent()) {
            UserEntity updatedUser = userEntity.get();
            updatedUser.setName(user.getName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setAge(user.getAge());
            updatedUser.setRole(user.getRole());
            return userRepository.save(updatedUser);
        }
        return null;

    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Autowired
    BranchRepository branchRepository;

    public List<UserEntity> getUserByBranchId (Long branchId){
        return branchRepository.findById(branchId)
                .map(BranchEntity::getUserEntities).orElseThrow(() -> new EntityNotFoundException("Car not found"));
    }

}
