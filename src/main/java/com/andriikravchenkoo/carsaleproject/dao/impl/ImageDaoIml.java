package com.andriikravchenkoo.carsaleproject.dao.impl;

import com.andriikravchenkoo.carsaleproject.dao.ImageDao;
import com.andriikravchenkoo.carsaleproject.dao.mapper.ImageRowMapper;
import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ImageDaoIml implements ImageDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final KeyHolder keyHolder;

    @Override
    public Image save(Image image) {
        final String SQL = "INSERT INTO images (name, type, data) VALUES (:name, :type, :data)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", image.getName())
                .addValue("type", image.getType())
                .addValue("data", image.getData());
        namedParameterJdbcTemplate.update(SQL, sqlParameterSource, keyHolder);
        long id = (long) keyHolder.getKeys().get("id");
        image.setId(id);
        return image;
    }

    @Override
    public List<Image> saveAll(List<Image> images) {
        final String SQL = "INSERT INTO images (name, type, data) VALUES (:name, :type, :data)";
        return images.stream()
                .map(image -> {
                    SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                            .addValue("name", image.getName())
                            .addValue("type", image.getType())
                            .addValue("data", image.getData());
                    namedParameterJdbcTemplate.update(SQL, sqlParameterSource, keyHolder);
                    long id = (long) keyHolder.getKeys().get("id");
                    image.setId(id);
                    return image;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Long saveUserImage(User user) {
        final String SQL = "INSERT INTO users_images (user_id, image_id) VALUES (:user_id, :image_id)";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("user_id", user.getId())
                .addValue("image_id", user.getImage().getId());
        return (long) namedParameterJdbcTemplate.update(SQL, sqlParameterSource);
    }

    @Override
    public Long saveAllDealershipImages(Dealership dealership) {
        final String SQL = "INSERT INTO dealerships_images (dealership_id, image_id) VALUES (:dealership_id, :image_id)";
        List<SqlParameterSource> parameterSources = dealership.getImages().stream()
                .map(image -> new MapSqlParameterSource()
                        .addValue("dealership_id", dealership.getId())
                        .addValue("image_id", image.getId()))
                .collect(Collectors.toList());
        return (long) namedParameterJdbcTemplate.batchUpdate(SQL, parameterSources.toArray(new SqlParameterSource[0])).length;
    }

    @Override
    public Long saveAllAnnouncementImages(Announcement announcement) {
        final String SQL = "INSERT INTO announcements_images (announcement_id, image_id) VALUES (:announcement_id, :image_id)";
        List<SqlParameterSource> parameterSources = announcement.getImages().stream()
                .map(image -> new MapSqlParameterSource()
                        .addValue("announcement_id", announcement.getId())
                        .addValue("image_id", announcement.getImages()))
                .collect(Collectors.toList());
        return (long) namedParameterJdbcTemplate.batchUpdate(SQL, parameterSources.toArray(new SqlParameterSource[0])).length;
    }

    @Override
    public Optional<Image> findById(Long id) {
        final String SQL = "SELECT * FROM images WHERE id = :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SQL, sqlParameterSource, new ImageRowMapper()));
    }
}
