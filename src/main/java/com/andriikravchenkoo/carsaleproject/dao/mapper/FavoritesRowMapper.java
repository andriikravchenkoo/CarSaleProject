package com.andriikravchenkoo.carsaleproject.dao.mapper;

import com.andriikravchenkoo.carsaleproject.model.entity.Favorites;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FavoritesRowMapper implements RowMapper<Favorites> {

  @Override
  public Favorites mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Favorites.builder()
        .id(rs.getLong("id"))
        .userId(rs.getLong("user_id"))
        .announcementId(rs.getLong("announcement_id"))
        .build();
  }
}
