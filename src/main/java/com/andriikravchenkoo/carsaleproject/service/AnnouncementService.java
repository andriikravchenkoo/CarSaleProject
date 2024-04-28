package com.andriikravchenkoo.carsaleproject.service;

import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;

import java.util.List;

public interface AnnouncementService {

    List<Announcement> findAll();

    List<Announcement> findAllByDateForPage(Long limitPerPage, Long offset);

    Announcement findById(Long id);

    Announcement save(Announcement announcement);

    void delete(Announcement announcement);

    boolean checkOwner(Long announcementId, Long userId);

    Long findTotalCount();
}
