package com.andriikravchenkoo.carsaleproject.dao;

import com.andriikravchenkoo.carsaleproject.model.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {

  List<User> findAll();

  Optional<User> findById(Long id);

  Optional<User> findByEmail(String email);

  Optional<User> findByDealershipId(Long id);

  Optional<User> findByAnnouncementId(Long id);

  User save(User user);

  User saveDealership(User user);

  void delete(User user);
}
