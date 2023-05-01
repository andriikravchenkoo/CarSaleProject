package com.andriikravchenkoo.carsaleproject.model.entity;

import com.andriikravchenkoo.carsaleproject.model.enums.BodyType;
import com.andriikravchenkoo.carsaleproject.model.enums.Color;
import com.andriikravchenkoo.carsaleproject.model.enums.EngineType;
import com.andriikravchenkoo.carsaleproject.model.enums.Transmission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {

    private Long id;

    private String vin;

    private String brand;

    private String model;

    private BodyType bodyType;

    private Integer year;

    private EngineType engineType;

    private Double engineCapacity;

    private Integer horsepower;

    private Transmission transmission;

    private Integer mileage;

    private Color color;

    private String licensePlate;

    private Boolean isUsed;

    private Announcement announcement;

    private Dealership dealership;

    public Vehicle(String vin, String brand, String model, BodyType bodyType, Integer year, EngineType engineType, Double engineCapacity, Integer horsepower, Transmission transmission, Integer mileage, Color color, String licensePlate, Boolean isUsed) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.bodyType = bodyType;
        this.year = year;
        this.engineType = engineType;
        this.engineCapacity = engineCapacity;
        this.horsepower = horsepower;
        this.transmission = transmission;
        this.mileage = mileage;
        this.color = color;
        this.licensePlate = licensePlate;
        this.isUsed = isUsed;
    }
}
