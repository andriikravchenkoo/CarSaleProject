package com.andriikravchenkoo.carsaleproject.service;

import com.andriikravchenkoo.carsaleproject.model.entity.Vehicle;
import java.util.List;

public interface VehicleService {

  List<Vehicle> findAll();

  List<Vehicle> findAllByUserId(Long id);

  Vehicle findById(Long id);

  Vehicle findByAnnouncementId(Long id);

  Vehicle save(Vehicle vehicle);

  Long updateAllWithNewDealerships(List<Vehicle> vehicles);

  void delete(Vehicle vehicle);
}
