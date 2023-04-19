package com.andriikravchenkoo.carsaleproject.service;

import com.andriikravchenkoo.carsaleproject.model.entity.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> findAll();

    Vehicle findById(Long id);

    Vehicle save(Vehicle vehicle);

    void delete(Vehicle vehicle);
}
