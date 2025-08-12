package com.andriikravchenkoo.carsaleproject.service;

import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;

import java.util.List;

public interface DealershipService {

    List<Dealership> findAll();

    List<Dealership> findAllByDate(Long limitPerPage, Long offset);

    Dealership findById(Long id);

    Dealership findByUserEmail(String email);

    Dealership findByVehicleId(Long vehicleId);

    Long findTotalCount();

    List<Dealership> findAllByFilter(Long limitPerPage, Long offset, String query, String region);

    Long findTotalCountByFilter(String query, String region);

    Dealership save(Dealership dealership);

    void delete(Dealership dealership);
}
