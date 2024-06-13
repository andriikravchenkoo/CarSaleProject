package com.andriikravchenkoo.carsaleproject.facade.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.andriikravchenkoo.carsaleproject.dto.UserProfilePageDto;
import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.service.ImageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class UserServiceFacadeImplTest {

    @InjectMocks private UserServiceFacadeImpl userServiceFacadeImpl;

    @Mock private ImageService imageService;

    private User testUser;
    private Image testImage;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");

        testImage = new Image();
        testImage.setId(1L);
    }

    @Test
    void testShouldSuccessfullyReturnUserWithImage() throws IOException {
        when(imageService.findByUserId(testUser.getId())).thenReturn(testImage);

        UserProfilePageDto userProfilePageDto = userServiceFacadeImpl.getUserWithImage(testUser);

        assertAll(
                () -> assertNotNull(userProfilePageDto),
                () -> assertEquals(testImage, testUser.getImage()));

        verify(imageService).findByUserId(testUser.getId());
    }

    @Test
    void testShouldFailReturnUserWithImageWhenResourceNotFound() throws IOException {
        String expectedErrorMessage = "Image by user id = " + testImage.getId() + " not found";

        when(imageService.findByUserId(testUser.getId()))
                .thenThrow(new IOException(expectedErrorMessage));

        IOException exception =
                assertThrows(
                        IOException.class, () -> userServiceFacadeImpl.getUserWithImage(testUser));

        assertEquals(expectedErrorMessage, exception.getMessage());

        verify(imageService).findByUserId(testUser.getId());
    }
}
