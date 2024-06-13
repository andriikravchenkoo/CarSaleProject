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
    public List<Announcement> findAllByDate(Long limitPerPage, Long offset) {
        return announcementDao.findAllByDate(limitPerPage, offset);
    }

    @Override
    public List<Announcement> findAllByUserId(Long limitPerPage, Long offset, Long userId) {
        return announcementDao.findAllByUserId(limitPerPage, offset, userId);
    }

    @Override
    public List<Announcement> findAllFavoritesByUserId(
            Long limitPerPage, Long offset, Long userId) {
        return announcementDao.findAllFavoritesByUserId(limitPerPage, offset, userId);
    }

    @Override
    public List<Announcement> findAllByVehicleUsage(
            Long limitPerPage, Long offset, Boolean isUsed) {
        return announcementDao.findAllByVehicleUsage(limitPerPage, offset, isUsed);
    }

    @Override
    public List<Announcement> findAllByDealershipId(
            Long limitPerPage, Long offset, Long dealershipId) {
        return announcementDao.findAllByDealershipId(limitPerPage, offset, dealershipId);
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
    public Boolean checkOwner(Long announcementId, Long userId) {
        return announcementDao.checkIsOwner(announcementId, userId);
    }

    @Override
    public Long findTotalCount() {
        return announcementDao.findTotalCount();
    }

    @Override
    public Long findTotalCountBuUserId(Long userId) {
        return announcementDao.findTotalCountByUserId(userId);
    }

    @Override
    public Long findTotalCountByVehicleUsage(Boolean isUsed) {
        return announcementDao.findTotalCountByVehicleUsage(isUsed);
    }

    @Override
    public Long findTotalCountByDealershipId(Long dealershipId) {
        return announcementDao.findTotalCountByDealershipId(dealershipId);
    }
}
