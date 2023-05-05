package com.andriikravchenkoo.carsaleproject.service.impl;

import com.andriikravchenkoo.carsaleproject.dao.ImageDao;
import com.andriikravchenkoo.carsaleproject.exception.ImageConvertException;
import com.andriikravchenkoo.carsaleproject.exception.ImageNotSavedException;
import com.andriikravchenkoo.carsaleproject.exception.ResourceNotFoundException;
import com.andriikravchenkoo.carsaleproject.model.entity.Announcement;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.service.ImageService;
import com.andriikravchenkoo.carsaleproject.util.ImageCompressor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageDao imageDao;

    @Override
    public Image save(MultipartFile file) throws IOException {
        if (file.getSize() == 0) {
            throw new ImageNotSavedException("Upload one photo");
        }

        return imageDao.save(Image.toEntity(file));
    }

    @Override
    public List<Image> saveAll(List<MultipartFile> files) {
        if (files.get(0).getSize() == 0) {
            throw new ImageNotSavedException("Upload at least one photo");
        }

        List<Image> images = files.stream().map(file -> {
            try {
                return Image.toEntity(file);
            } catch (IOException e) {
                throw new ImageConvertException("Failed convert Image = " + file.getOriginalFilename());
            }
        }).toList();

        return imageDao.saveAll(images);
    }

    @Override
    public Long saveUserImage(User user) {
        return imageDao.saveUserImage(user);
    }

    @Override
    public Long saveAllDealershipImages(Dealership dealership) {
        return imageDao.saveAllDealershipImages(dealership);
    }

    @Override
    public Long saveAllAnnouncementImages(Announcement announcement) {
        return imageDao.saveAllAnnouncementImages(announcement);
    }

    @Override
    public Image findById(Long id) throws IOException {
        Image image = imageDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image with id = " + id + " not found"));
        return Image.builder()
                .id(image.getId())
                .name(image.getName())
                .type(image.getType())
                .data(ImageCompressor.decompress(image.getData()))
                .build();
    }
}
