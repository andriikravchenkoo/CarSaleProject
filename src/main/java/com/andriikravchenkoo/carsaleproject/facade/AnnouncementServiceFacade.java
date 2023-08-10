package com.andriikravchenkoo.carsaleproject.facade;

import com.andriikravchenkoo.carsaleproject.dto.AnnouncementPageDto;
import com.andriikravchenkoo.carsaleproject.dto.VehicleAnnouncementDto;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AnnouncementServiceFacade {

  void createAnnouncement(
      VehicleAnnouncementDto vehicleAnnouncementDto, List<MultipartFile> files, User user);

  AnnouncementPageDto getAnnouncementWithImages(Long announcementId, User user) throws IOException;

  void deleteAnnouncement(Long announcementId);

  void addAnnouncementToFavorites(Long announcementId, Long userId);

  void removeAnnouncementFromFavorites(Long announcementId, Long currentUser);
}
