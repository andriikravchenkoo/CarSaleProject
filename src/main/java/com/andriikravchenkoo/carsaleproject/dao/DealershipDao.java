package com.andriikravchenkoo.carsaleproject.dao;

import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;

import java.util.List;
import java.util.Optional;

public interface DealershipDao {

    List<Dealership> findAll();

    List<Dealership> findAllByDate(Long limitPerPage, Long offset);

    Optional<Dealership> findById(Long id);

    Optional<Dealership> findByVehicleId(Long id);

    Optional<Dealership> findByUserEmail(String email);

    Long findTotalCount();

    Dealership save(Dealership dealership);

    void delete(Dealership dealership);
}
