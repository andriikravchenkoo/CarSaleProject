package com.andriikravchenkoo.carsaleproject.dto;

import com.andriikravchenkoo.carsaleproject.model.entity.*;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementWithFavoritesDto {

  private Long id;

  private Integer price;

  private Date created;

  private Boolean isClosed;

  private String description;

  private List<Image> images;

  private User user;

  private Vehicle vehicle;

  private Boolean isFavorite;

  public AnnouncementWithFavoritesDto(
      Integer price,
      Date created,
      Boolean isClosed,
      String description,
      List<Image> images,
      User user,
      Vehicle vehicle,
      Boolean isFavorite) {
    this.price = price;
    this.created = created;
    this.isClosed = isClosed;
    this.description = description;
    this.images = images;
    this.user = user;
    this.vehicle = vehicle;
    this.isFavorite = isFavorite;
  }
}
