package com.andriikravchenkoo.carsaleproject.dao.mapper;

import com.andriikravchenkoo.carsaleproject.model.entity.Vehicle;
import com.andriikravchenkoo.carsaleproject.model.enums.BodyType;
import com.andriikravchenkoo.carsaleproject.model.enums.Color;
import com.andriikravchenkoo.carsaleproject.model.enums.EngineType;
import com.andriikravchenkoo.carsaleproject.model.enums.Transmission;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class VehicleRowMapper implements RowMapper<Vehicle> {

  @Override
  public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Vehicle.builder()
        .id(rs.getLong("id"))
        .vin(rs.getString("vin"))
        .brand(rs.getString("brand"))
        .model(rs.getString("model"))
        .bodyType(BodyType.valueOf(rs.getString("body_type")))
        .year(rs.getInt("year"))
        .engineType(EngineType.valueOf(rs.getString("engine_type")))
        .engineCapacity(rs.getDouble("engine_capacity"))
        .horsepower(rs.getInt("horsepower"))
        .transmission(Transmission.valueOf(rs.getString("transmission")))
        .mileage(rs.getInt("mileage"))
        .color(Color.valueOf(rs.getString("color")))
        .licensePlate(rs.getString("license_plate"))
        .isUsed(rs.getBoolean("is_used"))
        .build();
  }
}
