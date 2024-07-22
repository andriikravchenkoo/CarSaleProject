package com.andriikravchenkoo.carsaleproject.security.facade.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.andriikravchenkoo.carsaleproject.dto.RegisterRequestDto;
import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.service.ImageService;
import com.andriikravchenkoo.carsaleproject.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceFacadeImplTest {

    @InjectMocks private RegistrationServiceFacadeImpl registrationServiceFacadeImpl;

    @Mock private UserService userService;

    @Mock private ImageService imageService;

    @Mock private PasswordEncoder passwordEncoder;

    private RegisterRequestDto testRegisterRequestDto;
    private MultipartFile testFile;
    private User testUser;
    private Image testImage;

    @BeforeEach
    void setUp() {
        testRegisterRequestDto = new RegisterRequestDto();
        testRegisterRequestDto.setEmail("test@example.com");
        testRegisterRequestDto.setPassword("password");

        testFile = mock(MultipartFile.class);

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");

        testImage = new Image();
        testImage.setId(1L);
    }

    @Test
    void testShouldSuccessfullyRegisterUser() throws IOException {
        when(userService.save(any(User.class))).thenReturn(testUser);
        when(imageService.save(any(MultipartFile.class))).thenReturn(testImage);

        registrationServiceFacadeImpl.register(testRegisterRequestDto, testFile);

        assertEquals(testImage, testUser.getImage());

        verify(userService).save(any(User.class));
        verify(imageService).save(testFile);
        verify(imageService).saveUserImage(testUser);
    }

    @Test
    void testShouldFailWhenImageServiceThrowException() throws IOException {
        String expectedErrorMessage = "Image by user id = " + testImage.getId() + " not found";

        when(userService.save(any(User.class))).thenReturn(testUser);
        when(imageService.save(any(MultipartFile.class)))
                .thenThrow(new IOException(expectedErrorMessage));

        IOException exception =
                assertThrows(
                        IOException.class,
                        () ->
                                registrationServiceFacadeImpl.register(
                                        testRegisterRequestDto, testFile));

        assertEquals(expectedErrorMessage, exception.getMessage());

        verify(userService).save(any(User.class));
        verify(imageService).save(testFile);
        verify(imageService, never()).saveUserImage(testUser);
    }
}
