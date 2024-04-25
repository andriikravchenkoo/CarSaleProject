package com.andriikravchenkoo.carsaleproject.dao.mapper;

import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AnnouncementRowMapper implements RowMapper<Announcement> {

    @Override
    public Announcement mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Announcement.builder()
                .id(rs.getLong("id"))
                .price(rs.getInt("price"))
                .created(rs.getDate("created"))
                .isClosed(rs.getBoolean("is_closed"))
                .description(rs.getString("description"))
                .build();
    }
}
