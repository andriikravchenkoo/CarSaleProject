package com.andriikravchenkoo.carsaleproject.service;

import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;

import java.util.List;

public interface DealershipService {

    List<Dealership> findAll();

    List<Dealership> findAllByDateForPage(Long limitPerPage, Long offset);

    Dealership findById(Long id);

    Dealership findByUserEmail(String email);

    Dealership findByVehicleId(Long vehicleId);

    Long findTotalCount();

    Dealership save(Dealership dealership);

    void delete(Dealership dealership);
}
