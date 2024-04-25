package com.andriikravchenkoo.carsaleproject.dto;

import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;
import com.andriikravchenkoo.carsaleproject.model.entity.Vehicle;
import com.andriikravchenkoo.carsaleproject.model.enums.BodyType;
import com.andriikravchenkoo.carsaleproject.model.enums.Color;
import com.andriikravchenkoo.carsaleproject.model.enums.EngineType;
import com.andriikravchenkoo.carsaleproject.model.enums.Transmission;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleAnnouncementDto {

    @Pattern(regexp = "\\b[(A-H|J-N|P|R-Z|0-9)]{17}\\b", message = "VIN not valid")
    private String vin;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;

    private BodyType bodyType;

    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Minimum year for a vehicle 1900")
    @Max(value = 2023, message = "Maximum year for a vehicle 2023")
    private Integer year;

    private EngineType engineType;

    @NotNull(message = "Engine capacity is required")
    @Min(value = 0, message = "Minimum engine capacity for a vehicle 0")
    @Max(value = 15, message = "Maximum engine capacity for a vehicle 15")
    private Double engineCapacity;

    @NotNull(message = "Horsepower is required")
    @Min(value = 10, message = "Minimum horsepower for a vehicle 10")
    @Max(value = 1500, message = "Maximum horsepower for a vehicle 1500")
    private Integer horsepower;

    private Transmission transmission;

    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Minimum mileage for a vehicle 0")
    @Max(value = 1000000, message = "Maximum mileage for a vehicle 1000000")
    private Integer mileage;

    private Color color;

    @Pattern(regexp = "^[A-Z]{2}\\s\\d{4}\\s[A-Z]{2}$", message = "License plate not valid")
    private String licensePlate;

    private Boolean isUsed;

    @NotNull(message = "Price is required")
    @Min(value = 100, message = "Minimum price for a vehicle 100$")
    private Integer price;

    @NotBlank(message = "Description is required")
    private String description;

    public Announcement toAnnouncementEntity() {
        return Announcement.builder()
                .price(this.getPrice())
                .created(new Date())
                .isClosed(false)
                .description(this.getDescription())
                .build();
    }

    public Vehicle toVehicleEntity() {
        return Vehicle.builder()
                .vin(this.getVin())
                .brand(this.getBrand())
                .model(this.getModel())
                .bodyType(this.getBodyType())
                .year(this.getYear())
                .engineType(this.getEngineType())
                .engineCapacity(this.getEngineCapacity())
                .horsepower(this.getHorsepower())
                .transmission(this.getTransmission())
                .mileage(this.getMileage())
                .color(this.getColor())
                .licensePlate(this.getLicensePlate())
                .isUsed(this.getIsUsed())
                .build();
    }
}
