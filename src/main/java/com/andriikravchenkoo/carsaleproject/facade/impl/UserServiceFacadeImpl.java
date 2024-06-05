package com.andriikravchenkoo.carsaleproject.facade.impl;

import com.andriikravchenkoo.carsaleproject.dto.UserProfilePageDto;
import com.andriikravchenkoo.carsaleproject.facade.UserServiceFacade;
import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.service.ImageService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceFacadeImpl implements UserServiceFacade {

    private final ImageService imageService;

    @Override
    public UserProfilePageDto getUserWithImage(User authenticationUser) throws IOException {
        Image image = imageService.findByUserId(authenticationUser.getId());
        authenticationUser.setImage(image);
        return authenticationUser.toDto();
    }
}
