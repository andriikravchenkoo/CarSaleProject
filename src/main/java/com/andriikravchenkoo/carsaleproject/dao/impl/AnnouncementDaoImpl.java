package com.andriikravchenkoo.carsaleproject.dao.impl;

import com.andriikravchenkoo.carsaleproject.dao.AnnouncementDao;
import com.andriikravchenkoo.carsaleproject.dao.mapper.AnnouncementRowMapper;
import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class AnnouncementDaoImpl implements AnnouncementDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final KeyHolder keyHolder;

    @Override
    public List<Announcement> findAll() {
        final String SQL = "SELECT * FROM announcements";
        return namedParameterJdbcTemplate.query(SQL, new AnnouncementRowMapper());
    }

    @Override
    public Optional<Announcement> findById(Long id) {
        final String SQL = "SELECT * FROM announcements WHERE id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL, sqlParameterSource, new AnnouncementRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Announcement by this id not found");
        }
        return Optional.empty();
    }

    @Override
    public Announcement save(Announcement announcement) {
        final String SQL = "INSERT INTO announcements (price, created, is_closed, description, user_id, vehicle_id) VALUES (:price, :created, :is_closed, :description, :user_id, :vehicle_id)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("price", announcement.getPrice())
                .addValue("created", announcement.getCreated())
                .addValue("is_closed", announcement.getIsClosed())
                .addValue("description", announcement.getDescription())
                .addValue("user_id", announcement.getUser().getId())
                .addValue("vehicle_id", announcement.getVehicle().getId());
        namedParameterJdbcTemplate.update(SQL, sqlParameterSource, keyHolder);
        long id = (long) keyHolder.getKeys().get("id");
        announcement.setId(id);
        return announcement;
    }

    @Override
    public void delete(Announcement announcement) {
        final String SQL = "DELETE FROM announcements WHERE id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", announcement.getId());
        namedParameterJdbcTemplate.update(SQL, sqlParameterSource);
    }
}
