package com.andriikravchenkoo.carsaleproject.service;

import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;

import java.util.List;

public interface AnnouncementService {

    List<Announcement> findAll();

    List<Announcement> findAllByDate(Long limitPerPage, Long offset);

    List<Announcement> findAllByUserId(Long limitPerPage, Long offset, Long userId);

    List<Announcement> findAllFavoritesByUserId(Long limitPerPage, Long offset, Long userId);

    List<Announcement> findAllByVehicleUsage(Long limitPerPage, Long offset, Boolean isUsed);

    List<Announcement> findAllByDealershipId(Long limitPerPage, Long offset, Long dealershipId);

    Announcement findById(Long id);

    Announcement save(Announcement announcement);

    void delete(Announcement announcement);

    Boolean checkOwner(Long announcementId, Long userId);

    Long findTotalCount();

    Long findTotalCountBuUserId(Long userId);

    Long findTotalCountByVehicleUsage(Boolean isUsed);

    Long findTotalCountByDealershipId(Long dealershipId);
}
