package com.andriikravchenkoo.carsaleproject.facade;

import com.andriikravchenkoo.carsaleproject.dto.AnnouncementWithFavoritesDto;
import com.andriikravchenkoo.carsaleproject.dto.VehicleAnnouncementDto;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AnnouncementServiceFacade {

  void createAnnouncement(
      VehicleAnnouncementDto vehicleAnnouncementDto, List<MultipartFile> files, User user);

  AnnouncementWithFavoritesDto getAnnouncementWithImages(Long id, User user) throws IOException;

  void addAnnouncementToFavorites(Long announcementId, Long userId);

  void removeAnnouncementFromFavorites(Long announcementId, Long currentUser);
}
