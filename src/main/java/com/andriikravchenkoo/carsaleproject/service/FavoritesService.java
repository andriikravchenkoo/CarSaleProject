package com.andriikravchenkoo.carsaleproject.service;

import com.andriikravchenkoo.carsaleproject.model.entity.Favorites;

public interface FavoritesService {

  Favorites save(Favorites favorites);

  void delete(Favorites favorites);

  void deleteAllByAnnouncementId(Long announcementId);

  boolean checkExistence(Favorites favorites);
}
