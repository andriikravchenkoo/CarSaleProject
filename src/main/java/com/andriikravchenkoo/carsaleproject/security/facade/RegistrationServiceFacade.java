package com.andriikravchenkoo.carsaleproject.security.facade;

import com.andriikravchenkoo.carsaleproject.dto.RegisterRequestDto;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface RegistrationServiceFacade {

  void register(RegisterRequestDto registerRequestDto, MultipartFile file) throws IOException;
}
