package com.andriikravchenkoo.carsaleproject.service.impl;

import com.andriikravchenkoo.carsaleproject.dao.FavoritesDao;
import com.andriikravchenkoo.carsaleproject.model.entity.Favorites;
import com.andriikravchenkoo.carsaleproject.service.FavoritesService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {

    private final FavoritesDao favoritesDao;

    @Override
    public Favorites save(Favorites favorites) {
        return favoritesDao.save(favorites);
    }

    @Override
    public void delete(Favorites favorites) {
        favoritesDao.delete(favorites);
    }

    @Override
    public void deleteAllByAnnouncementId(Long announcementId) {
        favoritesDao.deleteAllByAnnouncementId(announcementId);
    }

    @Override
    public boolean checkExistence(Favorites favorites) {
        Long result = favoritesDao.checkExistence(favorites);
        return result != null && result > 0;
    }

    @Override
    public Long findTotalCountByUserId(Long userId) {
        return favoritesDao.findTotalCountByUserId(userId);
    }
}
