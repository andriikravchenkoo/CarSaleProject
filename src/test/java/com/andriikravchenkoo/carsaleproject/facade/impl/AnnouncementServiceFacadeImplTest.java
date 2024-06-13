package com.andriikravchenkoo.carsaleproject.facade.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.andriikravchenkoo.carsaleproject.dto.AnnouncementPageDto;
import com.andriikravchenkoo.carsaleproject.dto.VehicleAnnouncementCreateDto;
import com.andriikravchenkoo.carsaleproject.exception.ResourceNotFoundException;
import com.andriikravchenkoo.carsaleproject.model.entity.*;
import com.andriikravchenkoo.carsaleproject.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AnnouncementServiceFacadeImplTest {

    @InjectMocks private AnnouncementServiceFacadeImpl announcementServiceFacadeImpl;

    @Mock private AnnouncementService announcementService;

    @Mock private VehicleService vehicleService;

    @Mock private DealershipService dealershipService;

    @Mock private ImageService imageService;

    @Mock private UserService userService;

    @Mock private FavoritesService favoritesService;

    private User testUser;
    private Dealership testDealership;
    private Vehicle testVehicle;
    private Announcement testAnnouncement;
    private Image testImage;
    private List<MultipartFile> testFiles;
    private List<Image> testImages;
    private VehicleAnnouncementCreateDto testVehicleAnnouncementCreateDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@exampl.com");

        testDealership = new Dealership();
        testDealership.setId(1L);

        testVehicle = new Vehicle();
        testVehicle.setId(1L);

        testAnnouncement = new Announcement();
        testAnnouncement.setId(1L);

        testImage = new Image();
        testImage.setId(1L);

        testFiles = new ArrayList<>();

        testImages = Collections.singletonList(new Image());

        testVehicleAnnouncementCreateDto = new VehicleAnnouncementCreateDto();
    }

    @Test
    void testShouldSuccessfullyCreateAnnouncement() {
        when(dealershipService.findByUserEmail(any(String.class))).thenReturn(testDealership);
        when(imageService.saveAll(anyList())).thenReturn(testImages);
        when(vehicleService.save(any(Vehicle.class))).thenReturn(testVehicle);
        when(announcementService.save(any(Announcement.class))).thenReturn(testAnnouncement);

        Long announcementId =
                announcementServiceFacadeImpl.createAnnouncement(
                        testVehicleAnnouncementCreateDto, testFiles, testUser);

        assertEquals(testAnnouncement.getId(), announcementId);

        verify(imageService).saveAllAnnouncementImages(testAnnouncement);
    }

    @Test
    void testShouldFailCreateAnnouncementWhenDealershipNotFound() {
        String expectedErrorMessage =
                "Dealership by user email = "
                        + testUser.getEmail()
                        + " not found. You are not listed in any of the dealership";

        when(dealershipService.findByUserEmail(testUser.getEmail()))
                .thenThrow(new ResourceNotFoundException(expectedErrorMessage));

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () ->
                                announcementServiceFacadeImpl.createAnnouncement(
                                        testVehicleAnnouncementCreateDto, testFiles, testUser));

        assertEquals(expectedErrorMessage, exception.getLocalizedMessage());
    }

    @Test
    void testShouldSuccessfullyReturnAnnouncementWithImages() throws IOException {
        when(imageService.findAllByAnnouncementId(anyLong())).thenReturn(testImages);
        when(announcementService.findById(anyLong())).thenReturn(testAnnouncement);
        when(vehicleService.findByAnnouncementId(anyLong())).thenReturn(testVehicle);
        when(userService.findByAnnouncementId(anyLong())).thenReturn(testUser);
        when(imageService.findByUserId(anyLong())).thenReturn(testImage);
        when(dealershipService.findByUserEmail(anyString())).thenReturn(testDealership);
        when(favoritesService.checkIsExistence(any(Favorites.class))).thenReturn(false);
        when(announcementService.checkOwner(anyLong(), anyLong())).thenReturn(false);

        AnnouncementPageDto result =
                announcementServiceFacadeImpl.getAnnouncementWithImages(
                        testAnnouncement.getId(), testUser.getId());

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(testAnnouncement.getId(), result.getId()),
                () -> assertEquals(testImages, result.getImages()),
                () -> assertEquals(testImage, result.getUser().getImage()),
                () -> assertFalse(result.getIsFavorite()));
        assertNotNull(result);
    }

    @Test
    void testShouldFailReturnAnnouncementWithImagesWhenAnnouncementNotFound() {
        String expectedErrorMassage =
                "Announcement with id = " + testAnnouncement.getId() + " not found";

        when(announcementService.findById(testAnnouncement.getId()))
                .thenThrow(new ResourceNotFoundException(expectedErrorMassage));

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () ->
                                announcementServiceFacadeImpl.getAnnouncementWithImages(
                                        testAnnouncement.getId(), testUser.getId()));

        assertEquals(expectedErrorMassage, exception.getLocalizedMessage());
    }

    @Test
    void testShouldSuccessfullyReturnAllAnnouncementsByDate() {
        Long limitPerPage = 6L;
        Long offset = 0L;

        when(announcementService.findAllByDate(limitPerPage, offset))
                .thenReturn(Collections.singletonList(testAnnouncement));
        when(imageService.findAllByAnnouncementId(anyLong())).thenReturn(testImages);
        when(vehicleService.findByAnnouncementId(anyLong())).thenReturn(testVehicle);
        when(dealershipService.findByVehicleId(anyLong())).thenReturn(testDealership);

        List<AnnouncementPageDto> result =
                announcementServiceFacadeImpl.getAllAnnouncementsByDate(limitPerPage, offset);
        AnnouncementPageDto announcementPageDto = result.get(0);

        assertAll(
                () -> assertNotNull(result),
                () -> assertFalse(result.isEmpty()),
                () -> assertEquals(testAnnouncement.getId(), announcementPageDto.getId()));

        verify(announcementService).findAllByDate(limitPerPage, offset);
        verify(imageService).findAllByAnnouncementId(testAnnouncement.getId());
        verify(vehicleService).findByAnnouncementId(testAnnouncement.getId());
        verify(dealershipService).findByVehicleId(testVehicle.getId());
    }

    @Test
    void testShouldSuccessfullyDeleteAnnouncement() {
        when(vehicleService.findByAnnouncementId(testAnnouncement.getId())).thenReturn(testVehicle);
        when(announcementService.findById(testAnnouncement.getId())).thenReturn(testAnnouncement);

        announcementServiceFacadeImpl.deleteAnnouncement(testAnnouncement.getId());

        verify(favoritesService).deleteAllByAnnouncementId(testAnnouncement.getId());
        verify(imageService).deleteAllAnnouncementImages(testAnnouncement);
        verify(announcementService).delete(testAnnouncement);
        verify(vehicleService).delete(testVehicle);
    }
}
