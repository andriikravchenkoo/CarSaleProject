package com.andriikravchenkoo.carsaleproject.repository;

import com.andriikravchenkoo.carsaleproject.model.entity.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
}
