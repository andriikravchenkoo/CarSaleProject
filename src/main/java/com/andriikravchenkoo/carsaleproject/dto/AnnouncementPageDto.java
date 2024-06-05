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
            Long id, Integer price, List<Image> images, Vehicle vehicle, Dealership dealership) {
        this.id = id;
        this.price = price;
        this.images = images;
        this.vehicle = vehicle;
        this.dealership = dealership;
    }
}
