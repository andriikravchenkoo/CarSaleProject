package com.andriikravchenkoo.carsaleproject.dto;

import com.andriikravchenkoo.carsaleproject.model.entity.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementPageDto {

    private Long id;

    private Integer price;

    private Date created;

    private Boolean isClosed;

    private String description;

    private List<Image> images;

    private User user;

    private Vehicle vehicle;

    private Dealership dealership;

    private Boolean isFavorite;

    private Boolean isOwner;

    public AnnouncementPageDto(
            Integer price,
            Date created,
            Boolean isClosed,
            String description,
            List<Image> images,
            User user,
            Vehicle vehicle,
            Dealership dealership,
            Boolean isFavorite,
            Boolean isOwner) {
        this.price = price;
        this.created = created;
        this.isClosed = isClosed;
        this.description = description;
        this.images = images;
        this.user = user;
        this.vehicle = vehicle;
        this.dealership = dealership;
        this.isFavorite = isFavorite;
        this.isOwner = isOwner;
    }
}
