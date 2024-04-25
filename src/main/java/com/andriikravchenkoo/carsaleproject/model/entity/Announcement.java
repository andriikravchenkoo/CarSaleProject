package com.andriikravchenkoo.carsaleproject.model.entity;

import com.andriikravchenkoo.carsaleproject.dto.AnnouncementPageDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announcement {

    private Long id;

    private Integer price;

    private Date created;

    private Boolean isClosed;

    private String description;

    private List<Image> images;

    private User user;

    private Vehicle vehicle;

    public Announcement(Integer price, Date created, Boolean isClosed, String description) {
        this.price = price;
        this.created = created;
        this.isClosed = isClosed;
        this.description = description;
    }

    public AnnouncementPageDto toDto(Boolean isFavorite, Boolean isOwner) {
        return AnnouncementPageDto.builder()
                .id(this.getId())
                .price(this.getPrice())
                .created(this.getCreated())
                .isClosed(this.getIsClosed())
                .description(this.getDescription())
                .images(this.getImages())
                .user(this.getUser())
                .vehicle(this.getVehicle())
                .isFavorite(isFavorite)
                .isOwner(isOwner)
                .build();
    }
}
