package com.andriikravchenkoo.carsaleproject.dao.impl;

import com.andriikravchenkoo.carsaleproject.dao.ImageDao;
import com.andriikravchenkoo.carsaleproject.dao.mapper.ImageRowMapper;
import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ImageDaoIml implements ImageDao {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private final KeyHolder keyHolder;

  @Override
  public Optional<Image> findById(Long id) {
    final String SQL = "SELECT * FROM images WHERE id = :id";
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
    try {
      return Optional.ofNullable(
          namedParameterJdbcTemplate.queryForObject(SQL, sqlParameterSource, new ImageRowMapper()));
    } catch (EmptyResultDataAccessException exception) {
      log.error("Image by this id not found");
    }
    return Optional.empty();
  }

  @Override
  public Optional<Image> findByUserId(Long id) {
    final String SQL =
        "SELECT i.* FROM images i INNER JOIN users_images ui ON ui.image_id = i.id WHERE ui.user_id = :id";
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
    try {
      return Optional.ofNullable(
          namedParameterJdbcTemplate.queryForObject(SQL, sqlParameterSource, new ImageRowMapper()));
    } catch (EmptyResultDataAccessException exception) {
      log.error("Image by this user id not found");
    }
    return Optional.empty();
  }

  @Override
  public List<Image> findAllByDealershipId(Long id) {
    final String SQL =
        "SELECT i.* FROM images i JOIN dealerships_images di ON i.id = di.image_id JOIN dealerships d ON d.id = di.dealership_id WHERE d.id = :id";
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
    return namedParameterJdbcTemplate.query(SQL, sqlParameterSource, new ImageRowMapper());
  }

  @Override
  public List<Image> findAllByAnnouncementId(Long id) {
    final String SQL =
        "SELECT i.* FROM images i JOIN announcements_images ai ON i.id = ai.image_id JOIN announcements a ON a.id = ai.announcement_id WHERE a.id = :id";
    SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
    return namedParameterJdbcTemplate.query(SQL, sqlParameterSource, new ImageRowMapper());
  }

  @Override
  public Integer findRowCount() {
    final String SQL = "SELECT COUNT(*) FROM images";
    return namedParameterJdbcTemplate.queryForObject(SQL, new HashMap<>(), Integer.class);
  }

  @Override
  public Image save(Image image) {
    final String SQL = "INSERT INTO images (name, type, data) VALUES (:name, :type, :data)";
    SqlParameterSource sqlParameterSource =
        new MapSqlParameterSource()
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
        .map(
            image -> {
              SqlParameterSource sqlParameterSource =
                  new MapSqlParameterSource()
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
    SqlParameterSource sqlParameterSource =
        new MapSqlParameterSource()
            .addValue("user_id", user.getId())
            .addValue("image_id", user.getImage().getId());
    return (long) namedParameterJdbcTemplate.update(SQL, sqlParameterSource);
  }

  @Override
  public Long saveAllDealershipImages(Dealership dealership) {
    final String SQL =
        "INSERT INTO dealerships_images (dealership_id, image_id) VALUES (:dealership_id, :image_id)";
    List<SqlParameterSource> sqlParameterSources =
        dealership.getImages().stream()
            .map(
                image ->
                    new MapSqlParameterSource()
                        .addValue("dealership_id", dealership.getId())
                        .addValue("image_id", image.getId()))
            .collect(Collectors.toList());
    return (long)
        namedParameterJdbcTemplate.batchUpdate(
                SQL, sqlParameterSources.toArray(new SqlParameterSource[0]))
            .length;
  }

  @Override
  public Long saveAllAnnouncementImages(Announcement announcement) {
    final String SQL =
        "INSERT INTO announcements_images (announcement_id, image_id) VALUES (:announcement_id, :image_id)";
    List<SqlParameterSource> sqlParameterSources =
        announcement.getImages().stream()
            .map(
                image ->
                    new MapSqlParameterSource()
                        .addValue("announcement_id", announcement.getId())
                        .addValue("image_id", image.getId()))
            .collect(Collectors.toList());
    return (long)
        namedParameterJdbcTemplate.batchUpdate(
                SQL, sqlParameterSources.toArray(new SqlParameterSource[0]))
            .length;
  }

  @Override
  public void deleteAllAnnouncementImages(Announcement announcement) {
    final String SQL = "DELETE FROM announcements_images WHERE announcement_id = :announcement_id";
    SqlParameterSource sqlParameterSource =
        new MapSqlParameterSource("announcement_id", announcement.getId());
    namedParameterJdbcTemplate.update(SQL, sqlParameterSource);
  }
}
