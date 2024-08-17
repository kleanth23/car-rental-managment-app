package com.car_rental_managment_app.entities;


import com.car_rental_managment_app.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "car_id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "lastName")
    String lastName;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "age")
    Integer age;

    @Column(name = "role")
    Role role;
}
