package com.andriikravchenkoo.carsaleproject.dao;

import com.andriikravchenkoo.carsaleproject.model.entity.Favorites;

public interface FavoritesDao {

    Favorites save(Favorites favorites);

    void delete(Favorites favorites);

    void deleteAllByAnnouncementId(Long announcementId);

    Boolean checkIsExistence(Favorites favorites);

    Long findTotalCountByUserId(Long userId);
}
