package com.andriikravchenkoo.carsaleproject.facade;

import com.andriikravchenkoo.carsaleproject.dto.VehicleAnnouncementDto;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnnouncementServiceFacade {

    void createAnnouncement(VehicleAnnouncementDto vehicleAnnouncementDto, List<MultipartFile> files, User user);
}
