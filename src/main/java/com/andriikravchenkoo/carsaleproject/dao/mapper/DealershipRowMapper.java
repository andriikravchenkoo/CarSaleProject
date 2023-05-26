package com.andriikravchenkoo.carsaleproject.dao.mapper;

import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.model.enums.Region;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class DealershipRowMapper implements RowMapper<Dealership> {

  @Override
  public Dealership mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Dealership.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("name"))
        .region(Region.valueOf(rs.getString("region")))
        .address(rs.getString("address"))
        .phoneNumber(rs.getString("phone_number"))
        .description(rs.getString("description"))
        .build();
  }
}
