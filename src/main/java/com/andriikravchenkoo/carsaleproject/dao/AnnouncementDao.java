package com.andriikravchenkoo.carsaleproject.dao;

import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;

import java.util.List;
import java.util.Optional;

public interface AnnouncementDao {

    List<Announcement> findAll();

    List<Announcement> findAllByDate(Long limitPerPage, Long offset);

    List<Announcement> findAllByUserId(Long limitPerPage, Long offset, Long userId);

    List<Announcement> findAllFavoritesByUserId(Long limitPerPage, Long offset, Long userId);

    List<Announcement> findAllByVehicleUsage(Long limitPerPage, Long offset, Boolean isUsed);

    Optional<Announcement> findById(Long id);

    Announcement save(Announcement announcement);

    void delete(Announcement announcement);

    Long checkOwner(Long announcementId, Long userId);

    Long findTotalCount();

    Long findTotalCountByUserId(Long userId);

    Long findTotalCountByVehicleUsage(Boolean isUsed);
}
