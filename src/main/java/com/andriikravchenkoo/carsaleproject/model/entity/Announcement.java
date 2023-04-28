package com.andriikravchenkoo.carsaleproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {

    private Long id;

    private Integer price;

    private Date created;

    private Boolean isClosed;

    private String description;

    private User user;

    private Vehicle vehicle;

    public Announcement(Integer price, Date created, Boolean isClosed, String description) {
        this.price = price;
        this.created = created;
        this.isClosed = isClosed;
        this.description = description;
    }
}
