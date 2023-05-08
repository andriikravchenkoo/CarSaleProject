package com.andriikravchenkoo.carsaleproject.facade.impl;

import com.andriikravchenkoo.carsaleproject.dto.VehicleAnnouncementDto;
import com.andriikravchenkoo.carsaleproject.facade.AnnouncementServiceFacade;
import com.andriikravchenkoo.carsaleproject.model.entity.*;
import com.andriikravchenkoo.carsaleproject.service.AnnouncementService;
import com.andriikravchenkoo.carsaleproject.service.DealershipService;
import com.andriikravchenkoo.carsaleproject.service.ImageService;
import com.andriikravchenkoo.carsaleproject.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceFacadeImpl implements AnnouncementServiceFacade {

    private final AnnouncementService announcementService;

    private final VehicleService vehicleService;

    private final DealershipService dealershipService;

    private final ImageService imageService;

    @Override
    @Transactional
    public void createAnnouncement(VehicleAnnouncementDto vehicleAnnouncementDto, List<MultipartFile> files, User user) {
        Vehicle vehicle = Vehicle.toEntity(vehicleAnnouncementDto);

        Dealership dealership = dealershipService.findByUserEmail(user.getEmail());

        vehicle.setDealership(dealership);

        List<Image> images = imageService.saveAll(files);

        Announcement announcement = Announcement.toEntity(vehicleAnnouncementDto);

        announcement.setImages(images);

        announcement.setUser(user);

        Vehicle savedVehicle = vehicleService.save(vehicle);

        announcement.setVehicle(savedVehicle);

        Announcement savedAnnouncement = announcementService.save(announcement);

        imageService.saveAllAnnouncementImages(savedAnnouncement);
    }
}
