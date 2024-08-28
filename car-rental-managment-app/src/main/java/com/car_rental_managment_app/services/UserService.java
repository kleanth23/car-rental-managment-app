package com.car_rental_managment_app.services;

import com.car_rental_managment_app.entities.BranchEntity;
import com.car_rental_managment_app.entities.UserEntity;
import com.car_rental_managment_app.exceptions.BranchNotFoundException;
import com.car_rental_managment_app.exceptions.UserNotFoundException;
import com.car_rental_managment_app.repository.BranchRepository;
import com.car_rental_managment_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BranchRepository branchRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, BranchRepository branchRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.branchRepository = branchRepository;
    }

    public UserEntity saveUser(UserEntity user, Long branchId) {
        try {
            Optional<BranchEntity> branchEntity = branchRepository.findById(branchId);
            if (branchEntity.isEmpty()) {
                throw new BranchNotFoundException("Branch with given id does not exist");
            }
            user.setBranchEntity(branchEntity.get());
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage(), e);
        }
    }

    public void registerUser(UserEntity user) {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("Email is already in use");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error registering user: " + e.getMessage(), e);
        }
    }

    public Optional<UserEntity> findUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by email: " + e.getMessage(), e);
        }
    }

    public Optional<UserEntity> loginUser(String email, String password) {
        try {
            Optional<UserEntity> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                if (passwordEncoder.matches(password, user.getPassword())) {
                    return Optional.of(user);
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error logging in user: " + e.getMessage(), e);
        }
    }

    public Optional<UserEntity> findById(Long userId) {
        try {
            return userRepository.findById(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by id: " + e.getMessage(), e);
        }
    }

    @Transactional
    public UserEntity updateUser(UserEntity user, Long userId) {
        try {
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
            } else {
                throw new UserNotFoundException("User with given id does not exist");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

    public void deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User with given id does not exist");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }

    public List<UserEntity> getUserByBranchId(Long branchId) {
        try {
            return branchRepository.findById(branchId)
                    .map(BranchEntity::getUserEntities)
                    .orElseThrow(() -> new BranchNotFoundException("Branch with given id does not exist"));
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving users by branch id: " + e.getMessage(), e);
        }
    }
}
