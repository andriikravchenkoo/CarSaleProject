package com.andriikravchenkoo.carsaleproject.dao;

import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;
import java.util.List;
import java.util.Optional;

public interface AnnouncementDao {

  List<Announcement> findAll();

  Optional<Announcement> findById(Long id);

  Announcement save(Announcement announcement);

  void delete(Announcement announcement);
}
