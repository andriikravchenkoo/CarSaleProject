package com.andriikravchenkoo.carsaleproject.facade;

import com.andriikravchenkoo.carsaleproject.dto.AnnouncementPageDto;
import com.andriikravchenkoo.carsaleproject.dto.VehicleAnnouncementCreateDto;
import com.andriikravchenkoo.carsaleproject.model.entity.User;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnnouncementServiceFacade {

    void createAnnouncement(
            VehicleAnnouncementCreateDto vehicleAnnouncementCreateDto,
            List<MultipartFile> files,
            User user);

    AnnouncementPageDto getAnnouncementWithImages(Long announcementId, User user)
            throws IOException;

    List<AnnouncementPageDto> getAllAnnouncementsByDate(Long limitPerPage, Long offset);

    void deleteAnnouncement(Long announcementId);

    void addAnnouncementToFavorites(Long announcementId, Long userId);

    void removeAnnouncementFromFavorites(Long announcementId, Long currentUser);

    Long getTotalCountAnnouncements();
}
