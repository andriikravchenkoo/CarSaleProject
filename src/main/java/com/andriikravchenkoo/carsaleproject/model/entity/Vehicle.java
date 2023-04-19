package com.andriikravchenkoo.carsaleproject.model.entity;

import com.andriikravchenkoo.carsaleproject.model.enums.BodyType;
import com.andriikravchenkoo.carsaleproject.model.enums.Color;
import com.andriikravchenkoo.carsaleproject.model.enums.EngineType;
import com.andriikravchenkoo.carsaleproject.model.enums.Transmission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("vehicles")
public class Vehicle {

    @Id
    private Long id;

    @Column("vin")
    private String vin;

    @Column("brand")
    private String brand;

    @Column("model")
    private String model;

    @Column("body_type")
    private BodyType bodyType;

    @Column("year")
    private Integer year;

    @Column("engine_type")
    private EngineType engineType;

    @Column("engine_capacity")
    private Double engineCapacity;

    @Column("horsepower")
    private Integer horsepower;

    @Column("transmission")
    private Transmission transmission;

    @Column("mileage")
    private Integer mileage;

    @Column("color")
    private Color color;

    @Column("license_plate")
    private String licensePlate;

    @Column("is_used")
    private Boolean isUsed;

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
