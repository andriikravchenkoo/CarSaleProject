package com.andriikravchenkoo.carsaleproject.dao.mapper;

import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.model.enums.Role;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .firstname(rs.getString("firstname"))
                .lastname(rs.getString("lastname"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .phoneNumber(rs.getString("phone_number"))
                .role(Role.valueOf(rs.getString("role")))
                .build();
    }
}
