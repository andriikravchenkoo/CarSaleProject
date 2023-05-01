package com.andriikravchenkoo.carsaleproject.dao;

import com.andriikravchenkoo.carsaleproject.model.entity.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleDao {

    List<Vehicle> findAll();

    Optional<Vehicle> findById(Long id);

    Vehicle save(Vehicle vehicle);

    void delete(Vehicle vehicle);
}
