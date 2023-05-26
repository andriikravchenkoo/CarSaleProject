package com.andriikravchenkoo.carsaleproject.service.impl;

import com.andriikravchenkoo.carsaleproject.dao.VehicleDao;
import com.andriikravchenkoo.carsaleproject.exception.DataAlreadyExistsException;
import com.andriikravchenkoo.carsaleproject.exception.ResourceNotFoundException;
import com.andriikravchenkoo.carsaleproject.model.entity.Vehicle;
import com.andriikravchenkoo.carsaleproject.service.VehicleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

  private final VehicleDao vehicleDao;

  @Override
  public List<Vehicle> findAll() {
    return vehicleDao.findAll();
  }

  @Override
  public List<Vehicle> findAllByUserId(Long id) {
    return vehicleDao.findAllByUserId(id);
  }

  @Override
  public Vehicle findById(Long id) {
    return vehicleDao
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id = " + id + " not found"));
  }

  @Override
  public Vehicle findByAnnouncementId(Long id) {
    return vehicleDao
        .findByAnnouncementId(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException("Vehicle by announcement id = " + id + " not found"));
  }

  @Override
  public Vehicle save(Vehicle vehicle) {
    try {
      return vehicleDao.save(vehicle);
    } catch (DuplicateKeyException exception) {
      throw new DataAlreadyExistsException(
          "Vehicle with this VIN code or license plate already exists");
    }
  }

  @Override
  public Long updateAllWithNewDealerships(List<Vehicle> vehicles) {
    return vehicleDao.updateAllWithNewDealerships(vehicles);
  }

  @Override
  public void delete(Vehicle vehicle) {
    vehicleDao.delete(vehicle);
  }
}
