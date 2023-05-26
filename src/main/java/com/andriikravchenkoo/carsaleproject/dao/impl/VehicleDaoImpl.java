package com.andriikravchenkoo.carsaleproject.dao.impl;

import com.andriikravchenkoo.carsaleproject.dao.VehicleDao;
import com.andriikravchenkoo.carsaleproject.dao.mapper.VehicleRowMapper;
import com.andriikravchenkoo.carsaleproject.model.entity.Vehicle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class VehicleDaoImpl implements VehicleDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final KeyHolder keyHolder;

    @Override
    public List<Vehicle> findAll() {
        final String SQL = "SELECT * FROM vehicles";
        return namedParameterJdbcTemplate.query(SQL, new VehicleRowMapper());
    }

    @Override
    public List<Vehicle> findAllByUserId(Long id) {
        final String SQL = "SELECT * FROM vehicles v JOIN announcements a ON v.id = a.vehicle_id WHERE a.user_id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.query(SQL, sqlParameterSource, new VehicleRowMapper());
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        String SQL = "SELECT * FROM vehicles WHERE id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL, sqlParameterSource, new VehicleRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Vehicle by this id not found");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Vehicle> findByAnnouncementId(Long id) {
        String SQL = "SELECT v.* FROM vehicles v JOIN announcements a ON v.id = a.vehicle_id WHERE a.id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL, sqlParameterSource, new VehicleRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Vehicle by this announcement id not found");
        }
        return Optional.empty();
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        final String SQL = "INSERT INTO vehicles (vin, brand, model, body_type, year, engine_type, engine_capacity, horsepower, transmission, mileage, color, license_plate, is_used, dealership_id) VALUES (:vin, :brand, :model, :body_type, :year, :engine_type, :engine_capacity, :horsepower, :transmission, :mileage, :color, :license_plate, :is_used, :dealership_id)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("vin", vehicle.getVin())
                .addValue("brand", vehicle.getBrand())
                .addValue("model", vehicle.getModel())
                .addValue("body_type", vehicle.getBodyType(), Types.OTHER)
                .addValue("year", vehicle.getYear())
                .addValue("engine_type", vehicle.getEngineType(), Types.OTHER)
                .addValue("engine_capacity", vehicle.getEngineCapacity())
                .addValue("horsepower", vehicle.getHorsepower())
                .addValue("transmission", vehicle.getTransmission(), Types.OTHER)
                .addValue("mileage", vehicle.getMileage())
                .addValue("color", vehicle.getColor(), Types.OTHER)
                .addValue("license_plate", vehicle.getLicensePlate())
                .addValue("is_used", vehicle.getIsUsed())
                .addValue("dealership_id", vehicle.getDealership().getId());
        namedParameterJdbcTemplate.update(SQL, sqlParameterSource, keyHolder);
        long id = (long) keyHolder.getKeys().get("id");
        vehicle.setId(id);
        return vehicle;
    }

    @Override
    public Long updateAllWithNewDealerships(List<Vehicle> vehicles) {
        final String SQL = "UPDATE vehicles SET dealership_id = :dealership_id WHERE id = :id";
        List<SqlParameterSource> sqlParameterSources = vehicles.stream()
                .map(vehicle -> new MapSqlParameterSource()
                        .addValue("dealership_id", vehicle.getDealership().getId())
                        .addValue("id", vehicle.getId()))
                .collect(Collectors.toList());
        return (long) namedParameterJdbcTemplate.batchUpdate(SQL, sqlParameterSources.toArray(new SqlParameterSource[0])).length;
    }

    @Override
    public void delete(Vehicle vehicle) {
        final String SQL = "DELETE FROM vehicles WHERE id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", vehicle.getId());
        namedParameterJdbcTemplate.update(SQL, sqlParameterSource);
    }
}
