package com.andriikravchenkoo.carsaleproject.dao.impl;

import com.andriikravchenkoo.carsaleproject.dao.DealershipDao;
import com.andriikravchenkoo.carsaleproject.dao.mapper.DealershipRowMapper;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
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
    public Optional<Dealership> findById(Long id) {
        final String SQL = "SELECT * FROM dealerships WHERE id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL, sqlParameterSource, new DealershipRowMapper()));
    }

    @Override
    public Dealership save(Dealership dealership) {
        final String SQL = "INSERT INTO dealerships (name, region, address, phone_number, description) VALUES (:name, :region, :address, :phone_number, :description)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
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
