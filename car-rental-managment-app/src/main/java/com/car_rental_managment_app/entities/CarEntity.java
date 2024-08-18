package com.car_rental_managment_app.entities;

import com.car_rental_managment_app.enums.Colour;
import com.car_rental_managment_app.enums.Engine;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "car_id")
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "colour")
    private Colour colour;

    @Column(name = "model")
    private String model;

    @Column(name = "productionYear")
    private int productionYear;

    @Column(name = "licensePlate")
    private String licensePlate;

    @Column(name = "rentalPerDay")
    private String rentalPerDay;

    @Column(name = "engine")
    private Engine engine;

    @Column(name = "isAvailable")
    private boolean isAvailable;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id")
    private BranchEntity branchEntity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "carEntity")
    private List<ReservationEntity> reservationEntities;
}
