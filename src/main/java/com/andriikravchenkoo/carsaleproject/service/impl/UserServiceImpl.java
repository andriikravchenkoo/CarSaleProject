package com.andriikravchenkoo.carsaleproject.service.impl;

import com.andriikravchenkoo.carsaleproject.dao.UserDao;
import com.andriikravchenkoo.carsaleproject.exception.DataAlreadyExistsException;
import com.andriikravchenkoo.carsaleproject.exception.ResourceNotFoundException;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User by ID = " + id + " not found"));
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "User with email = " + email + " not found"));
    }

    @Override
    public User findByDealershipId(Long id) {
        return userDao.findByDealershipId(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "User by dealership id = " + id + " not found"));
    }

    @Override
    public User findByAnnouncementId(Long id) {
        return userDao.findByAnnouncementId(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "User by announcement id = " + id + " not found"));
    }

    @Override
    public User save(User user) {
        try {
            return userDao.save(user);
        } catch (DuplicateKeyException exception) {
            throw new DataAlreadyExistsException("Email or phone number already exists");
        }
    }

    @Override
    public User saveDealership(User user) {
        return userDao.saveDealership(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }
}
