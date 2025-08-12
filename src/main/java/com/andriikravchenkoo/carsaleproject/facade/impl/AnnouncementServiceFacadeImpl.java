package com.andriikravchenkoo.carsaleproject.facade.impl;

import com.andriikravchenkoo.carsaleproject.dto.AnnouncementPageDto;
import com.andriikravchenkoo.carsaleproject.dto.VehicleAnnouncementCreateDto;
import com.andriikravchenkoo.carsaleproject.facade.AnnouncementServiceFacade;
import com.andriikravchenkoo.carsaleproject.facade.filter.AnnouncementFilter;
import com.andriikravchenkoo.carsaleproject.model.entity.*;
import com.andriikravchenkoo.carsaleproject.service.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceFacadeImpl implements AnnouncementServiceFacade {

    private final AnnouncementService announcementService;

    private final VehicleService vehicleService;

    private final DealershipService dealershipService;

    private final ImageService imageService;

    private final UserService userService;

    private final FavoritesService favoritesService;

    @Override
    @Transactional
    public Long createAnnouncement(
            VehicleAnnouncementCreateDto vehicleAnnouncementCreateDto,
            List<MultipartFile> files,
            User authenticationUser) {
        Vehicle vehicle = vehicleAnnouncementCreateDto.toVehicleEntity();

        Dealership dealership = dealershipService.findByUserEmail(authenticationUser.getEmail());

        vehicle.setDealership(dealership);

        List<Image> images = imageService.saveAll(files);

        Announcement announcement = vehicleAnnouncementCreateDto.toAnnouncementEntity();

        announcement.setImages(images);

        announcement.setUser(authenticationUser);

        Vehicle savedVehicle = vehicleService.save(vehicle);

        announcement.setVehicle(savedVehicle);

        Announcement savedAnnouncement = announcementService.save(announcement);

        imageService.saveAllAnnouncementImages(savedAnnouncement);

        return savedAnnouncement.getId();
    }

    @Override
    public AnnouncementPageDto getAnnouncementWithImages(Long announcementId, Long userId)
            throws IOException {
        List<Image> images = imageService.findAllByAnnouncementId(announcementId);

        Announcement announcement = announcementService.findById(announcementId);

        Vehicle vehicle = vehicleService.findByAnnouncementId(announcementId);

        User ownerAnnouncement = userService.findByAnnouncementId(announcementId);

        Image image = imageService.findByUserId(ownerAnnouncement.getId());

        Dealership dealership = dealershipService.findByUserEmail(ownerAnnouncement.getEmail());

        ownerAnnouncement.setImage(image);

        ownerAnnouncement.setDealership(dealership);

        announcement.setImages(images);

        announcement.setUser(ownerAnnouncement);

        announcement.setVehicle(vehicle);

        Favorites favorites = new Favorites(userId, announcementId);

        return announcement.toDto(
                favoritesService.checkIsExistence(favorites),
                announcementService.checkOwner(announcementId, userId));
    }

    @Override
    public List<AnnouncementPageDto> getAllAnnouncementsByDate(Long limitPerPage, Long offset) {
        return announcementService.findAllByDate(limitPerPage, offset).stream()
                .map(this::mapToAnnouncementPageDto)
                .toList();
    }

    @Override
    public List<AnnouncementPageDto> getAllAnnouncementByUser(
            Long limitPerPage, Long offset, Long userId) {
        return announcementService.findAllByUserId(limitPerPage, offset, userId).stream()
                .map(this::mapToAnnouncementPageDto)
                .toList();
    }

    @Override
    public List<AnnouncementPageDto> getAllFavoritesAnnouncementsByUser(
            Long limitPerPage, Long offset, Long userId) {
        return announcementService.findAllFavoritesByUserId(limitPerPage, offset, userId).stream()
                .map(this::mapToAnnouncementPageDto)
                .toList();
    }

    @Override
    public List<AnnouncementPageDto> getAllAnnouncementByVehicleUsage(
            Long limitPerPage, Long offset, Boolean isUsed) {
        return announcementService.findAllByVehicleUsage(limitPerPage, offset, isUsed).stream()
                .map(this::mapToAnnouncementPageDto)
                .toList();
    }

    @Override
    public List<AnnouncementPageDto> getAllAnnouncementsByDealership(
            Long limitPerPage, Long offset, Long dealershipId) {
        return announcementService
                .findAllByDealershipId(limitPerPage, offset, dealershipId)
                .stream()
                .map(this::mapToAnnouncementPageDto)
                .toList();
    }

    @Override
    public List<AnnouncementPageDto> searchAnnouncements(
            Long limitPerPage, Long offset, AnnouncementFilter filter) {
        if (filter == null || !filter.hasAnyCriteria()) {
            return getAllAnnouncementsByDate(limitPerPage, offset);
        }

        // Prefer exact filters (favorites/user/dealership) first
        if (filter.getFavoritesForUserId() != null) {
            return getAllFavoritesAnnouncementsByUser(
                    limitPerPage, offset, filter.getFavoritesForUserId());
        }
        if (filter.getUserId() != null) {
            return getAllAnnouncementByUser(limitPerPage, offset, filter.getUserId());
        }
        if (filter.getDealershipId() != null) {
            return getAllAnnouncementsByDealership(limitPerPage, offset, filter.getDealershipId());
        }

        // Advanced filter goes to DAO via service
        return announcementService
                .findAllByAdvancedFilter(
                        limitPerPage,
                        offset,
                        filter.getQuery(),
                        filter.getBrand(),
                        filter.getModel(),
                        filter.getMinYear(),
                        filter.getMaxYear(),
                        filter.getMinPrice(),
                        filter.getMaxPrice(),
                        filter.getIsUsed(),
                        filter.getSort() != null ? filter.getSort().name() : "DATE_DESC")
                .stream()
                .map(this::mapToAnnouncementPageDto)
                .toList();
    }

    @Override
    public Long getTotalCountByAdvancedFilter(AnnouncementFilter filter) {
        if (filter == null || !filter.hasAnyCriteria()) {
            return announcementService.findTotalCount();
        }
        if (filter.getFavoritesForUserId() != null) {
            return favoritesService.findTotalCountByUserId(filter.getFavoritesForUserId());
        }
        if (filter.getUserId() != null) {
            return announcementService.findTotalCountBuUserId(filter.getUserId());
        }
        if (filter.getDealershipId() != null) {
            return announcementService.findTotalCountByDealershipId(filter.getDealershipId());
        }
        if (filter.getIsUsed() != null
                || filter.getQuery() != null
                || filter.getBrand() != null
                || filter.getModel() != null
                || filter.getMinYear() != null
                || filter.getMaxYear() != null
                || filter.getMinPrice() != null
                || filter.getMaxPrice() != null) {
            return announcementService.findTotalCountByAdvancedFilter(
                    filter.getQuery(),
                    filter.getBrand(),
                    filter.getModel(),
                    filter.getMinYear(),
                    filter.getMaxYear(),
                    filter.getMinPrice(),
                    filter.getMaxPrice(),
                    filter.getIsUsed());
        }
        return announcementService.findTotalCount();
    }

    private AnnouncementPageDto mapToAnnouncementPageDto(Announcement announcement) {
        List<Image> images = imageService.findAllByAnnouncementId(announcement.getId());
        Vehicle vehicle = vehicleService.findByAnnouncementId(announcement.getId());
        Dealership dealership = dealershipService.findByVehicleId(vehicle.getId());

        return new AnnouncementPageDto(
                announcement.getId(), announcement.getPrice(), images, vehicle, dealership);
    }

    @Override
    @Transactional
    public void deleteAnnouncement(Long announcementId) {
        Vehicle vehicle = vehicleService.findByAnnouncementId(announcementId);

        Announcement announcement = announcementService.findById(announcementId);

        favoritesService.deleteAllByAnnouncementId(announcementId);

        imageService.deleteAllAnnouncementImages(announcement);

        announcementService.delete(announcement);

        vehicleService.delete(vehicle);
    }

    @Override
    public void addAnnouncementToFavorites(Long announcementId, Long userId) {
        favoritesService.save(new Favorites(userId, announcementId));
    }

    @Override
    public void removeAnnouncementFromFavorites(Long announcementId, Long userId) {
        favoritesService.delete(new Favorites(userId, announcementId));
    }

    @Override
    public Long getTotalCountAnnouncements() {
        return announcementService.findTotalCount();
    }

    @Override
    public Long getTotalCountAnnouncementByUser(Long userId) {
        return announcementService.findTotalCountBuUserId(userId);
    }

    @Override
    public Long getTotalCountFavoritesAnnouncementByUser(Long userId) {
        return favoritesService.findTotalCountByUserId(userId);
    }

    @Override
    public Long getTotalCountAnnouncementByVehicleUsage(Boolean isUsed) {
        return announcementService.findTotalCountByVehicleUsage(isUsed);
    }

    @Override
    public Long getTotalCountAnnouncementsByDealershipId(Long dealershipId) {
        return announcementService.findTotalCountByDealershipId(dealershipId);
    }
}
