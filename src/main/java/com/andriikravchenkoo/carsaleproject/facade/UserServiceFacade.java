package com.andriikravchenkoo.carsaleproject.facade;

import com.andriikravchenkoo.carsaleproject.dto.UserProfilePageDto;
import com.andriikravchenkoo.carsaleproject.model.entity.User;

import java.io.IOException;

public interface UserServiceFacade {

    UserProfilePageDto getUserWithImage(User authenticationUser) throws IOException;
}
