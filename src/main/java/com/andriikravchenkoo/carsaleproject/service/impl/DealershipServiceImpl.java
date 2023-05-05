package com.andriikravchenkoo.carsaleproject.service.impl;

import com.andriikravchenkoo.carsaleproject.dao.DealershipDao;
import com.andriikravchenkoo.carsaleproject.exception.DataAlreadyExistsException;
import com.andriikravchenkoo.carsaleproject.exception.ResourceNotFoundException;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.service.DealershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealershipServiceImpl implements DealershipService {

    private final DealershipDao dealershipDao;

    @Override
    public List<Dealership> findAll() {
        return dealershipDao.findAll();
    }

    @Override
    public Dealership findById(Long id) {
        return dealershipDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dealership with id = " + id + " not found"));
    }

    @Override
    public Dealership save(Dealership dealership) {
        try {
            return dealershipDao.save(dealership);
        } catch (DuplicateKeyException exception) {
            throw new DataAlreadyExistsException("Phone number already exists");
        }
    }

    @Override
    public void delete(Dealership dealership) {
        dealershipDao.delete(dealership);
    }
}
