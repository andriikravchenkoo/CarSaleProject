package com.andriikravchenkoo.carsaleproject.dao;

import com.andriikravchenkoo.carsaleproject.model.entity.Vehicle;
import java.util.List;
import java.util.Optional;

public interface VehicleDao {

  List<Vehicle> findAll();

  List<Vehicle> findAllByUserId(Long id);

  Optional<Vehicle> findById(Long id);

  Optional<Vehicle> findByAnnouncementId(Long id);

  Vehicle save(Vehicle vehicle);

  Long updateAllWithNewDealerships(List<Vehicle> vehicles);

  void delete(Vehicle vehicle);
}
