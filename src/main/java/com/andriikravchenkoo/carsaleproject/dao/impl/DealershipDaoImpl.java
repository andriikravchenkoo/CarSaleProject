package com.andriikravchenkoo.carsaleproject.dao.impl;

import com.andriikravchenkoo.carsaleproject.dao.DealershipDao;
import com.andriikravchenkoo.carsaleproject.dao.mapper.DealershipRowMapper;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class DealershipDaoImpl implements DealershipDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final KeyHolder keyHolder;

    @Override
    public List<Dealership> findAll() {
        final String SQL = "SELECT * FROM dealerships";
        return namedParameterJdbcTemplate.query(SQL, new DealershipRowMapper());
    }

    @Override
    public List<Dealership> findAllByDate(Long limitPerPage, Long offset) {
        final String SQL =
                "SELECT * FROM dealerships ORDER BY id DESC LIMIT :limitPerPage OFFSET :offset";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
                        .addValue("limitPerPage", limitPerPage)
                        .addValue("offset", offset);
        return namedParameterJdbcTemplate.query(SQL, sqlParameterSource, new DealershipRowMapper());
    }

    @Override
    public Optional<Dealership> findById(Long id) {
        final String SQL = "SELECT * FROM dealerships WHERE id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            SQL, sqlParameterSource, new DealershipRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Dealership by this id not found");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Dealership> findByVehicleId(Long vehicleId) {
        final String SQL =
                "SELECT d.* FROM dealerships d JOIN vehicles v ON d.id = v.dealership_id WHERE v.id = :vehicle_id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("vehicle_id", vehicleId);
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            SQL, sqlParameterSource, new DealershipRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Dealership by this vehicle id not found");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Dealership> findByUserEmail(String email) {
        final String SQL =
                "SELECT d.id, d.name, d.region, d.address, d.phone_number, d.description FROM dealerships d JOIN users u on d.id = u.dealership_id WHERE u.email = :email";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("email", email);
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            SQL, sqlParameterSource, new DealershipRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Dealership by this user email not found");
        }
        return Optional.empty();
    }

    @Override
    public Long findTotalCount() {
        final String SQL = "SELECT COUNT(*) FROM dealerships";
        return namedParameterJdbcTemplate.queryForObject(SQL, new HashMap<>(), Long.class);
    }

    @Override
    public Dealership save(Dealership dealership) {
        final String SQL =
                "INSERT INTO dealerships (name, region, address, phone_number, description) VALUES (:name, :region, :address, :phone_number, :description)";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
                        .addValue("name", dealership.getName())
                        .addValue("region", dealership.getRegion(), Types.OTHER)
                        .addValue("address", dealership.getAddress())
                        .addValue("phone_number", dealership.getPhoneNumber())
                        .addValue("description", dealership.getDescription());
        namedParameterJdbcTemplate.update(SQL, sqlParameterSource, keyHolder);
        long id = (long) keyHolder.getKeys().get("id");
        dealership.setId(id);
        return dealership;
    }

    @Override
    public void delete(Dealership dealership) {
        final String SQL = "DELETE FROM dealerships WHERE id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", dealership.getId());
        namedParameterJdbcTemplate.update(SQL, sqlParameterSource);
    }
}
