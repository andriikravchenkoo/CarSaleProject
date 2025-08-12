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

    List<Announcement> findAllByDealershipId(Long limitPerPage, Long offset, Long dealershipId);

    Optional<Announcement> findById(Long id);

    Announcement save(Announcement announcement);

    void delete(Announcement announcement);

    Boolean checkIsOwner(Long announcementId, Long userId);

    Long findTotalCount();

    Long findTotalCountByUserId(Long userId);

    Long findTotalCountByVehicleUsage(Boolean isUsed);

    Long findTotalCountByDealershipId(Long dealershipId);

    List<Announcement> findAllByAdvancedFilter(
            Long limitPerPage,
            Long offset,
            String query,
            String brand,
            String model,
            Integer minYear,
            Integer maxYear,
            Integer minPrice,
            Integer maxPrice,
            Boolean isUsed,
            String sort);

    Long findTotalCountByAdvancedFilter(
            String query,
            String brand,
            String model,
            Integer minYear,
            Integer maxYear,
            Integer minPrice,
            Integer maxPrice,
            Boolean isUsed);
}
