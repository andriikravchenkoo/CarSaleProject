package com.andriikravchenkoo.carsaleproject.dao;

import com.andriikravchenkoo.carsaleproject.model.entity.Favorites;

public interface FavoritesDao {

    Favorites save(Favorites favorites);

    void delete(Favorites favorites);

    Long checkExistence(Favorites favorites);
}
