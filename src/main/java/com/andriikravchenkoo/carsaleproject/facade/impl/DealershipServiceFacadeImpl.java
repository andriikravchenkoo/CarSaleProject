package com.andriikravchenkoo.carsaleproject.facade.impl;

import com.andriikravchenkoo.carsaleproject.dto.DealershipDto;
import com.andriikravchenkoo.carsaleproject.facade.DealershipServiceFacade;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.service.DealershipService;
import com.andriikravchenkoo.carsaleproject.service.ImageService;
import com.andriikravchenkoo.carsaleproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealershipServiceFacadeImpl implements DealershipServiceFacade {

    private final DealershipService dealershipService;

    private final ImageService imageService;

    private final UserService userService;

    @Override
    @Transactional
    public void createDealership(DealershipDto dealershipDto, List<MultipartFile> files, User user) {
        Dealership dealership = dealershipService.save(Dealership.toEntity(dealershipDto));

        List<Image> images = imageService.saveAll(files);

        dealership.setImages(images);

        imageService.saveAllDealershipImages(dealership);

        user.setDealership(dealership);

        userService.saveDealership(user);
    }
}
