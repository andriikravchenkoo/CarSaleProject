package com.andriikravchenkoo.carsaleproject.facade;

import com.andriikravchenkoo.carsaleproject.dto.AnnouncementPageDto;
import com.andriikravchenkoo.carsaleproject.dto.VehicleAnnouncementCreateDto;
import com.andriikravchenkoo.carsaleproject.model.entity.User;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnnouncementServiceFacade {

    Long createAnnouncement(
            VehicleAnnouncementCreateDto vehicleAnnouncementCreateDto,
            List<MultipartFile> files,
            User user);

    AnnouncementPageDto getAnnouncementWithImages(Long announcementId, User authenticationUser)
            throws IOException;

    List<AnnouncementPageDto> getAllAnnouncementsByDate(Long limitPerPage, Long offset);

    List<AnnouncementPageDto> getAllAnnouncementByUser(Long limitPerPage, Long offset, User user);

    List<AnnouncementPageDto> getAllFavoritesAnnouncementsByUser(
            Long limitPerPage, Long offset, User user);

    List<AnnouncementPageDto> getAllAnnouncementByVehicleUsage(
            Long limitPerPage, Long offset, Boolean isUsed);

    void deleteAnnouncement(Long announcementId);

    void addAnnouncementToFavorites(Long announcementId, Long userId);

    void removeAnnouncementFromFavorites(Long announcementId, Long currentUser);

    Long getTotalCountAnnouncements();

    Long getTotalCountAnnouncementByUser(Long userId);

    Long getTotalCountFavoritesAnnouncementByUser(Long userId);

    Long getTotalCountAnnouncementByVehicleUsage(Boolean isUsed);
}
