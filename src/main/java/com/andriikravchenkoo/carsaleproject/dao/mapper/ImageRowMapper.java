package com.andriikravchenkoo.carsaleproject.dao.mapper;

import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageRowMapper implements RowMapper<Image> {

  @Override
  public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Image.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("name"))
        .type(rs.getString("type"))
        .data(rs.getBytes("data"))
        .build();
  }
}
