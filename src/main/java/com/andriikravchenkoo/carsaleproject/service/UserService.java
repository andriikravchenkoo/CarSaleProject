package com.andriikravchenkoo.carsaleproject.service;

import com.andriikravchenkoo.carsaleproject.model.entity.User;
import java.util.List;

public interface UserService {

  List<User> findAll();

  User findById(Long id);

  User findByEmail(String email);

  User findByDealershipId(Long id);

  User findByAnnouncementId(Long id);

  User save(User user);

  User saveDealership(User user);

  void delete(User user);
}
