package com.andriikravchenkoo.carsaleproject.service;

import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;

import java.util.List;

public interface AnnouncementService {

    List<Announcement> findAll();

    Announcement findById(Long id);

    Announcement save(Announcement announcement);

    void delete(Announcement announcement);
}
