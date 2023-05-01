package com.andriikravchenkoo.carsaleproject.dao;

import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;

import java.util.List;
import java.util.Optional;

public interface DealershipDao {

    List<Dealership> findAll();

    Optional<Dealership> findById(Long id);

    Dealership save(Dealership dealership);

    void delete(Dealership dealership);
}
