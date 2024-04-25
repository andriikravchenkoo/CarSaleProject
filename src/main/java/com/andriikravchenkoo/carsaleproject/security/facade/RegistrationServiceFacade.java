package com.andriikravchenkoo.carsaleproject.security.facade;

import com.andriikravchenkoo.carsaleproject.dto.RegisterRequestDto;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RegistrationServiceFacade {

    void register(RegisterRequestDto registerRequestDto, MultipartFile file) throws IOException;
}
