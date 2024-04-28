package com.andriikravchenkoo.carsaleproject.service.impl;

import com.andriikravchenkoo.carsaleproject.dao.AnnouncementDao;
import com.andriikravchenkoo.carsaleproject.exception.ResourceNotFoundException;
import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;
import com.andriikravchenkoo.carsaleproject.service.AnnouncementService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementDao announcementDao;

    @Override
    public List<Announcement> findAll() {
        return announcementDao.findAll();
    }

    @Override
    public List<Announcement> findAllByDateForPage(Long limitPerPage, Long offset) {
        return announcementDao.findAllByDate(limitPerPage, offset);
    }

    @Override
    public Announcement findById(Long id) {
        return announcementDao
                .findById(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "Announcement with id = " + id + " not found"));
    }

    @Override
    public Announcement save(Announcement announcement) {
        return announcementDao.save(announcement);
    }

    @Override
    public void delete(Announcement announcement) {
        announcementDao.delete(announcement);
    }

    @Override
    public boolean checkOwner(Long announcementId, Long userId) {
        return announcementDao.checkOwner(announcementId, userId) == 1;
    }

    @Override
    public Long findTotalCount() {
        return announcementDao.findTotalCount();
    }
}
