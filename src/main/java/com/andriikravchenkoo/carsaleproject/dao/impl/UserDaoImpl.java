package com.andriikravchenkoo.carsaleproject.dao.impl;

import com.andriikravchenkoo.carsaleproject.dao.UserDao;
import com.andriikravchenkoo.carsaleproject.dao.mapper.UserRowMapper;
import com.andriikravchenkoo.carsaleproject.model.entity.User;

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

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final KeyHolder keyHolder;

    @Override
    public List<User> findAll() {
        final String SQL = "SELECT * FROM users";
        return namedParameterJdbcTemplate.query(SQL, new UserRowMapper());
    }

    @Override
    public Optional<User> findById(Long id) {
        final String SQL = "SELECT * FROM users WHERE id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            SQL, sqlParameterSource, new UserRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.error("User by this id not found");
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        final String SQL = "SELECT * FROM users WHERE email = :email";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("email", email);
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            SQL, sqlParameterSource, new UserRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.error("User with this email not found");
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByDealershipId(Long id) {
        final String SQL = "SELECT * FROM users WHERE dealership_id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            SQL, sqlParameterSource, new UserRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.error("User with this dealership id not found");
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByAnnouncementId(Long id) {
        final String SQL =
                "SELECT u.* FROM users u JOIN announcements a ON u.id = a.user_id WHERE a.id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            SQL, sqlParameterSource, new UserRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            log.error("User with this announcement id not found");
        }
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        final String SQL =
                "INSERT INTO users (firstname, lastname, email, password, phone_number, role) VALUES (:firstname, :lastname, :email, :password, :phone_number, :role)";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
                        .addValue("firstname", user.getFirstname())
                        .addValue("lastname", user.getLastname())
                        .addValue("email", user.getEmail())
                        .addValue("password", user.getPassword())
                        .addValue("phone_number", user.getPhoneNumber())
                        .addValue("role", user.getRole(), Types.OTHER);
        namedParameterJdbcTemplate.update(SQL, sqlParameterSource, keyHolder);
        long id = (long) keyHolder.getKeys().get("id");
        user.setId(id);
        return user;
    }

    @Override
    public User saveDealership(User user) {
        final String SQL = "UPDATE users SET dealership_id = :dealership_id WHERE id = :id";
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
                        .addValue("dealership_id", user.getDealership().getId())
                        .addValue("id", user.getId());
        namedParameterJdbcTemplate.update(SQL, sqlParameterSource);
        return user;
    }

    @Override
    public void delete(User user) {
        final String SQL = "DELETE FROM users WHERE id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", user.getId());
        namedParameterJdbcTemplate.update(SQL, sqlParameterSource);
    }
}
