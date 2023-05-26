package com.andriikravchenkoo.carsaleproject.dao;

import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import java.util.List;
import java.util.Optional;

public interface ImageDao {

  Optional<Image> findById(Long id);

  Optional<Image> findByUserId(Long id);

  List<Image> findAllByDealershipId(Long id);

  List<Image> findAllByAnnouncementId(Long id);

  Image save(Image image);

  List<Image> saveAll(List<Image> images);

  Long saveUserImage(User user);

  Long saveAllDealershipImages(Dealership dealership);

  Long saveAllAnnouncementImages(Announcement announcement);
}
