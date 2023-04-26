package com.andriikravchenkoo.carsaleproject.dao.mapper;

import com.andriikravchenkoo.carsaleproject.model.entity.Vehicle;
import com.andriikravchenkoo.carsaleproject.model.enums.BodyType;
import com.andriikravchenkoo.carsaleproject.model.enums.Color;
import com.andriikravchenkoo.carsaleproject.model.enums.EngineType;
import com.andriikravchenkoo.carsaleproject.model.enums.Transmission;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VehicleRowMapper implements RowMapper<Vehicle> {

    @Override
    public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(rs.getLong("id"));
        vehicle.setVin(rs.getString("vin"));
        vehicle.setBrand(rs.getString("brand"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setBodyType(BodyType.valueOf(rs.getString("body_type")));
        vehicle.setYear(rs.getInt("year"));
        vehicle.setEngineType(EngineType.valueOf(rs.getString("engine_type")));
        vehicle.setEngineCapacity(rs.getDouble("engine_capacity"));
        vehicle.setHorsepower(rs.getInt("horsepower"));
        vehicle.setTransmission(Transmission.valueOf(rs.getString("transmission")));
        vehicle.setMileage(rs.getInt("mileage"));
        vehicle.setColor(Color.valueOf(rs.getString("color")));
        vehicle.setLicensePlate(rs.getString("license_plate"));
        vehicle.setIsUsed(rs.getBoolean("is_used"));
        return vehicle;
    }
}
