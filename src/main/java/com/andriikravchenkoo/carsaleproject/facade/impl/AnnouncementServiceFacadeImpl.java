package com.andriikravchenkoo.carsaleproject.facade.impl;

import com.andriikravchenkoo.carsaleproject.dto.AnnouncementWithFavoritesDto;
import com.andriikravchenkoo.carsaleproject.dto.VehicleAnnouncementDto;
import com.andriikravchenkoo.carsaleproject.facade.AnnouncementServiceFacade;
import com.andriikravchenkoo.carsaleproject.model.entity.*;
import com.andriikravchenkoo.carsaleproject.service.*;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
  public void createAnnouncement(
      VehicleAnnouncementDto vehicleAnnouncementDto, List<MultipartFile> files, User user) {
    Vehicle vehicle = vehicleAnnouncementDto.toVehicleEntity();

    Dealership dealership = dealershipService.findByUserEmail(user.getEmail());

    vehicle.setDealership(dealership);

    List<Image> images = imageService.saveAll(files);

    Announcement announcement = vehicleAnnouncementDto.toAnnouncementEntity();

    announcement.setImages(images);

    announcement.setUser(user);

    Vehicle savedVehicle = vehicleService.save(vehicle);

    announcement.setVehicle(savedVehicle);

    Announcement savedAnnouncement = announcementService.save(announcement);

    imageService.saveAllAnnouncementImages(savedAnnouncement);
  }

  @Override
  public AnnouncementWithFavoritesDto getAnnouncementWithImages(Long id, User currentUser)
      throws IOException {
    List<Image> images = imageService.findAllByAnnouncementId(id);

    Announcement announcement = announcementService.findById(id);

    Vehicle vehicle = vehicleService.findByAnnouncementId(id);

    User ownerAnnouncement = userService.findByAnnouncementId(id);

    Image image = imageService.findByUserId(ownerAnnouncement.getId());

    Dealership dealership = dealershipService.findByUserEmail(ownerAnnouncement.getEmail());

    ownerAnnouncement.setImage(image);

    ownerAnnouncement.setDealership(dealership);

    announcement.setImages(images);

    announcement.setUser(ownerAnnouncement);

    announcement.setVehicle(vehicle);

    Favorites favorites = new Favorites(currentUser.getId(), id);

    return announcement.toDto(favoritesService.checkExistence(favorites));
  }

  @Override
  public void addAnnouncementToFavorites(Long announcementId, Long userId) {
    favoritesService.save(new Favorites(userId, announcementId));
  }

  @Override
  public void removeAnnouncementFromFavorites(Long announcementId, Long userId) {
    favoritesService.delete(new Favorites(userId, announcementId));
  }
}
