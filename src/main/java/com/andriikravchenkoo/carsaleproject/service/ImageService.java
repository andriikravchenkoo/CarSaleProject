package com.andriikravchenkoo.carsaleproject.service;

import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    List<Image> findAllByDealershipId(Long id);

    Image findById(Long id) throws IOException;

    Image findByUserId(Long id) throws IOException;

    Image save(MultipartFile file) throws IOException;

    List<Image> saveAll(List<MultipartFile> files);

    Long saveUserImage(User user);

    Long saveAllDealershipImages(Dealership dealership);

    Long saveAllAnnouncementImages(Announcement announcement);
}
