package com.andriikravchenkoo.carsaleproject.security.facade.impl;

import com.andriikravchenkoo.carsaleproject.dto.RegisterRequestDto;
import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.security.facade.RegistrationServiceFacade;
import com.andriikravchenkoo.carsaleproject.service.ImageService;
import com.andriikravchenkoo.carsaleproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RegistrationServiceFacadeImpl implements RegistrationServiceFacade {

    private final UserService userService;

    private final ImageService imageService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(RegisterRequestDto registerRequestDto, MultipartFile file) throws IOException {
        User user = userService.save(User.toEntity(registerRequestDto, passwordEncoder));

        Image image = imageService.save(file);

        user.setImage(image);

        imageService.saveUserImage(user);
    }
}
