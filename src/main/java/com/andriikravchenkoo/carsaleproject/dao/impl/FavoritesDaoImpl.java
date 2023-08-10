package com.andriikravchenkoo.carsaleproject.dao.impl;

import com.andriikravchenkoo.carsaleproject.dao.FavoritesDao;
import com.andriikravchenkoo.carsaleproject.model.entity.Favorites;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FavoritesDaoImpl implements FavoritesDao {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private final KeyHolder keyHolder;

  @Override
  public Favorites save(Favorites favorites) {
    final String SQL =
        "INSERT INTO favorites (user_id, announcement_id) VALUES (:user_id, :announcement_id)";
    SqlParameterSource sqlParameterSource =
        new MapSqlParameterSource()
            .addValue("user_id", favorites.getUserId())
            .addValue("announcement_id", favorites.getAnnouncementId());
    namedParameterJdbcTemplate.update(SQL, sqlParameterSource, keyHolder);
    long id = (long) keyHolder.getKeys().get("id");
    favorites.setId(id);
    return favorites;
  }

  @Override
  public void delete(Favorites favorites) {
    final String SQL =
        "DELETE FROM favorites WHERE user_id = :user_id AND announcement_id = :announcement_id";
    SqlParameterSource sqlParameterSource =
        new MapSqlParameterSource()
            .addValue("user_id", favorites.getUserId())
            .addValue("announcement_id", favorites.getAnnouncementId());
    namedParameterJdbcTemplate.update(SQL, sqlParameterSource);
  }

  @Override
  public void deleteAllByAnnouncementId(Long announcementId) {
    final String SQL = "DELETE FROM favorites WHERE announcement_id = :announcement_id";
    SqlParameterSource sqlParameterSource =
        new MapSqlParameterSource("announcement_id", announcementId);
    namedParameterJdbcTemplate.update(SQL, sqlParameterSource);
  }

  @Override
  public Long checkExistence(Favorites favorites) {
    final String SQL =
        "SELECT COUNT(*) FROM favorites WHERE user_id = :user_id AND announcement_id = :announcement_id";
    SqlParameterSource sqlParameterSource =
        new MapSqlParameterSource()
            .addValue("user_id", favorites.getUserId())
            .addValue("announcement_id", favorites.getAnnouncementId());
    return namedParameterJdbcTemplate.queryForObject(SQL, sqlParameterSource, Long.class);
  }
}
