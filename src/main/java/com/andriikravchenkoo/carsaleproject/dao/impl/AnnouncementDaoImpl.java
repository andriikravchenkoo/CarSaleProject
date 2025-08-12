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

import java.util.HashMap;
import org.springframework.util.StringUtils;
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
    public List<Announcement> findAllByDate(Long limitPerPage, Long offset) {
        final String SQL =
                "SELECT * FROM announcements ORDER BY id DESC LIMIT :limitPerPage OFFSET :offset";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
                        .addValue("limitPerPage", limitPerPage)
                        .addValue("offset", offset);
        return namedParameterJdbcTemplate.query(
                SQL, sqlParameterSource, new AnnouncementRowMapper());
    }

    @Override
    public List<Announcement> findAllByUserId(Long limitPerPage, Long offset, Long userId) {
        final String SQL =
                "SELECT * FROM announcements WHERE user_id = :user_id ORDER BY id DESC LIMIT :limitPerPage OFFSET :offset";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
                        .addValue("limitPerPage", limitPerPage)
                        .addValue("offset", offset)
                        .addValue("user_id", userId);
        return namedParameterJdbcTemplate.query(
                SQL, sqlParameterSource, new AnnouncementRowMapper());
    }

    @Override
    public List<Announcement> findAllFavoritesByUserId(
            Long limitPerPage, Long offset, Long userId) {
        final String SQL =
                "SELECT * FROM announcements a JOIN favorites f ON a.id = f.announcement_id JOIN users u ON f.user_id = u.id WHERE u.id = :user_id ORDER BY a.id DESC LIMIT :limitPerPage OFFSET :offset";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
                        .addValue("limitPerPage", limitPerPage)
                        .addValue("offset", offset)
                        .addValue("user_id", userId);
        return namedParameterJdbcTemplate.query(
                SQL, sqlParameterSource, new AnnouncementRowMapper());
    }

    @Override
    public List<Announcement> findAllByVehicleUsage(
            Long limitPerPage, Long offset, Boolean isUsed) {
        final String SQL =
                "SELECT a.* FROM announcements AS a JOIN vehicles AS v ON a.vehicle_id = v.id WHERE v.is_used = :is_used ORDER BY a.id DESC LIMIT :limitPerPage OFFSET :offset";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
                        .addValue("limitPerPage", limitPerPage)
                        .addValue("offset", offset)
                        .addValue("is_used", isUsed);
        return namedParameterJdbcTemplate.query(
                SQL, sqlParameterSource, new AnnouncementRowMapper());
    }

    @Override
    public List<Announcement> findAllByDealershipId(
            Long limitPerPage, Long offset, Long dealershipId) {
        final String SQL =
                "SELECT a.* FROM announcements a JOIN users u ON a.user_id = u.id JOIN dealerships d ON u.dealership_id = d.id WHERE d.id = :dealership_id ORDER BY a.id DESC LIMIT :limitPerPage OFFSET :offset";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
                        .addValue("limitPerPage", limitPerPage)
                        .addValue("offset", offset)
                        .addValue("dealership_id", dealershipId);
        return namedParameterJdbcTemplate.query(
                SQL, sqlParameterSource, new AnnouncementRowMapper());
    }

    @Override
    public Optional<Announcement> findById(Long id) {
        final String SQL = "SELECT * FROM announcements WHERE id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            SQL, sqlParameterSource, new AnnouncementRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Announcement by this id not found");
        }
        return Optional.empty();
    }

    @Override
    public Announcement save(Announcement announcement) {
        final String SQL =
                "INSERT INTO announcements (price, created, is_closed, description, user_id, vehicle_id) VALUES (:price, :created, :is_closed, :description, :user_id, :vehicle_id)";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
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
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("id", announcement.getId());
        namedParameterJdbcTemplate.update(SQL, sqlParameterSource);
    }

    @Override
    public Boolean checkIsOwner(Long announcementId, Long userId) {
        final String SQL =
                "SELECT EXISTS (SELECT 1 FROM announcements AS a WHERE a.id = :announcement_id AND a.user_id = :user_id) AS is_owner";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
                        .addValue("announcement_id", announcementId)
                        .addValue("user_id", userId);
        return namedParameterJdbcTemplate.queryForObject(SQL, sqlParameterSource, Boolean.class);
    }

    @Override
    public Long findTotalCount() {
        final String SQL = "SELECT COUNT(*) FROM announcements";
        return namedParameterJdbcTemplate.queryForObject(SQL, new HashMap<>(), Long.class);
    }

    @Override
    public Long findTotalCountByUserId(Long userId) {
        final String SQL = "SELECT COUNT(*) FROM announcements WHERE user_id = :user_id";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource().addValue("user_id", userId);
        return namedParameterJdbcTemplate.queryForObject(SQL, sqlParameterSource, Long.class);
    }

    @Override
    public Long findTotalCountByVehicleUsage(Boolean isUsed) {
        final String SQL =
                "SELECT COUNT(*) FROM announcements a JOIN vehicles v ON a.vehicle_id = v.id WHERE v.is_used = :is_used";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource().addValue("is_used", isUsed);
        return namedParameterJdbcTemplate.queryForObject(SQL, sqlParameterSource, Long.class);
    }

    @Override
    public Long findTotalCountByDealershipId(Long dealershipId) {
        final String SQL =
                "SELECT COUNT(*) FROM announcements a JOIN users u ON a.user_id = u.id JOIN dealerships d ON u.dealership_id = d.id WHERE d.id = :dealership_id";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource().addValue("dealership_id", dealershipId);
        return namedParameterJdbcTemplate.queryForObject(SQL, sqlParameterSource, Long.class);
    }

    @Override
    public List<Announcement> findAllByAdvancedFilter(
            Long limitPerPage,
            Long offset,
            String query,
            String brand,
            String model,
            Integer minYear,
            Integer maxYear,
            Integer minPrice,
            Integer maxPrice,
            Boolean isUsed,
            String sort) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.* FROM announcements a JOIN vehicles v ON a.vehicle_id = v.id WHERE 1=1 ");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (StringUtils.hasText(query)) {
            sql.append(" AND (LOWER(v.brand) LIKE :q OR LOWER(v.model) LIKE :q) ");
            params.addValue("q", "%" + query.toLowerCase() + "%");
        }
        if (StringUtils.hasText(brand)) {
            sql.append(" AND LOWER(v.brand) = :brand ");
            params.addValue("brand", brand.toLowerCase());
        }
        if (StringUtils.hasText(model)) {
            sql.append(" AND LOWER(v.model) = :model ");
            params.addValue("model", model.toLowerCase());
        }
        if (minYear != null) {
            sql.append(" AND v.year >= :minYear ");
            params.addValue("minYear", minYear);
        }
        if (maxYear != null) {
            sql.append(" AND v.year <= :maxYear ");
            params.addValue("maxYear", maxYear);
        }
        if (minPrice != null) {
            sql.append(" AND a.price >= :minPrice ");
            params.addValue("minPrice", minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND a.price <= :maxPrice ");
            params.addValue("maxPrice", maxPrice);
        }
        if (isUsed != null) {
            sql.append(" AND v.is_used = :isUsed ");
            params.addValue("isUsed", isUsed);
        }

        String orderBy = " ORDER BY a.created DESC ";
        if (StringUtils.hasText(sort)) {
            switch (sort) {
                case "DATE_ASC" -> orderBy = " ORDER BY a.created ASC ";
                case "DATE_DESC" -> orderBy = " ORDER BY a.created DESC ";
                case "PRICE_ASC" -> orderBy = " ORDER BY a.price ASC ";
                case "PRICE_DESC" -> orderBy = " ORDER BY a.price DESC ";
                default -> orderBy = " ORDER BY a.created DESC ";
            }
        }
        sql.append(orderBy);
        sql.append(" LIMIT :limitPerPage OFFSET :offset ");

        params.addValue("limitPerPage", limitPerPage);
        params.addValue("offset", offset);

        return namedParameterJdbcTemplate.query(sql.toString(), params, new AnnouncementRowMapper());
    }

    @Override
    public Long findTotalCountByAdvancedFilter(
            String query,
            String brand,
            String model,
            Integer minYear,
            Integer maxYear,
            Integer minPrice,
            Integer maxPrice,
            Boolean isUsed) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM announcements a JOIN vehicles v ON a.vehicle_id = v.id WHERE 1=1 ");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (StringUtils.hasText(query)) {
            sql.append(" AND (LOWER(v.brand) LIKE :q OR LOWER(v.model) LIKE :q) ");
            params.addValue("q", "%" + query.toLowerCase() + "%");
        }
        if (StringUtils.hasText(brand)) {
            sql.append(" AND LOWER(v.brand) = :brand ");
            params.addValue("brand", brand.toLowerCase());
        }
        if (StringUtils.hasText(model)) {
            sql.append(" AND LOWER(v.model) = :model ");
            params.addValue("model", model.toLowerCase());
        }
        if (minYear != null) {
            sql.append(" AND v.year >= :minYear ");
            params.addValue("minYear", minYear);
        }
        if (maxYear != null) {
            sql.append(" AND v.year <= :maxYear ");
            params.addValue("maxYear", maxYear);
        }
        if (minPrice != null) {
            sql.append(" AND a.price >= :minPrice ");
            params.addValue("minPrice", minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND a.price <= :maxPrice ");
            params.addValue("maxPrice", maxPrice);
        }
        if (isUsed != null) {
            sql.append(" AND v.is_used = :isUsed ");
            params.addValue("isUsed", isUsed);
        }

        return namedParameterJdbcTemplate.queryForObject(sql.toString(), params, Long.class);
    }
}
