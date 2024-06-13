package com.andriikravchenkoo.carsaleproject.facade.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.andriikravchenkoo.carsaleproject.dto.DealershipCreateDto;
import com.andriikravchenkoo.carsaleproject.dto.DealershipPageDto;
import com.andriikravchenkoo.carsaleproject.exception.ResourceNotFoundException;
import com.andriikravchenkoo.carsaleproject.model.entity.*;
import com.andriikravchenkoo.carsaleproject.service.DealershipService;
import com.andriikravchenkoo.carsaleproject.service.ImageService;
import com.andriikravchenkoo.carsaleproject.service.UserService;
import com.andriikravchenkoo.carsaleproject.service.VehicleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DealershipServiceFacadeImplTest {

    @InjectMocks private DealershipServiceFacadeImpl dealershipServiceFacadeImpl;

    @Mock private DealershipService dealershipService;

    @Mock private ImageService imageService;

    @Mock private UserService userService;

    @Mock private VehicleService vehicleService;

    private User testUser;
    private Dealership testDealership;
    private Vehicle testVehicle;
    private Announcement testAnnouncement;
    private List<MultipartFile> testFiles;
    private List<Image> testImages;
    private DealershipCreateDto testDealershipCreateDto;

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

        testFiles = new ArrayList<>();

        testImages = Collections.singletonList(new Image());

        testDealershipCreateDto = new DealershipCreateDto();
    }

    @Test
    void testShouldSuccessfullyCreateDealership() {
        when(dealershipService.save(any(Dealership.class))).thenReturn(testDealership);
        when(imageService.saveAll(anyList())).thenReturn(testImages);

        Long dealershipId =
                dealershipServiceFacadeImpl.createDealership(
                        testDealershipCreateDto, testFiles, testUser);

        assertEquals(testDealership.getId(), dealershipId);

        verify(imageService).saveAllDealershipImages(testDealership);
        verify(userService).saveDealership(testUser);
    }

    @Test
    void testShouldSuccessfullyReturnDealershipWithImages() {
        when(imageService.findAllByDealershipId(anyLong())).thenReturn(testImages);
        when(dealershipService.findById(anyLong())).thenReturn(testDealership);
        when(userService.checkIsSellerInDealership(anyString(), anyLong())).thenReturn(true);

        DealershipPageDto result =
                dealershipServiceFacadeImpl.getDealershipWithImages(
                        testDealership.getId(), testUser);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(testDealership.getId(), result.getId()),
                () -> assertEquals(testImages, result.getImages()),
                () -> assertTrue(result.getIsSeller()));

        verify(imageService).findAllByDealershipId(testDealership.getId());
        verify(dealershipService).findById(testDealership.getId());
        verify(userService).checkIsSellerInDealership(testUser.getEmail(), testDealership.getId());
    }

    @Test
    void testShouldFailReturnDealershipWithImagesWhenDealershipNotFound() {
        String expectedErrorMessage =
                "Dealership with id = " + testDealership.getId() + " not found";

        when(dealershipService.findById(testDealership.getId()))
                .thenThrow(new ResourceNotFoundException(expectedErrorMessage));

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () ->
                                dealershipServiceFacadeImpl.getDealershipWithImages(
                                        testDealership.getId(), testUser));

        assertEquals(expectedErrorMessage, exception.getLocalizedMessage());

        verify(dealershipService).findById(testDealership.getId());
    }

    @Test
    void testShouldSuccessfullyReturnAllDealershipsByDate() {
        Long limitPerPage = 10L;
        Long offset = 0L;
        Long countOfVehicles = 5L;

        when(dealershipService.findAllByDate(limitPerPage, offset))
                .thenReturn(Collections.singletonList(testDealership));
        when(imageService.findAllByDealershipId(anyLong())).thenReturn(testImages);
        when(vehicleService.findCountByDealershipId(anyLong())).thenReturn(countOfVehicles);

        List<DealershipPageDto> result =
                dealershipServiceFacadeImpl.getAllDealershipsByDate(limitPerPage, offset);
        DealershipPageDto dealershipPageDto = result.get(0);

        assertAll(
                () -> assertNotNull(result),
                () -> assertFalse(result.isEmpty()),
                () -> assertEquals(testDealership.getId(), dealershipPageDto.getId()));

        verify(dealershipService).findAllByDate(limitPerPage, offset);
        verify(imageService).findAllByDealershipId(testDealership.getId());
        verify(vehicleService).findCountByDealershipId(testDealership.getId());
    }

    @Test
    void testShouldSuccessfullyBecomeSeller() {
        when(vehicleService.findAllByUserId(testUser.getId()))
                .thenReturn(Collections.singletonList(testVehicle));
        when(dealershipService.findById(testDealership.getId())).thenReturn(testDealership);

        dealershipServiceFacadeImpl.becomeSeller(testDealership.getId(), testUser);

        assertEquals(testDealership, testUser.getDealership());

        verify(vehicleService).findAllByUserId(testUser.getId());
        verify(dealershipService).findById(testDealership.getId());
        verify(vehicleService).updateAllWithNewDealerships(Collections.singletonList(testVehicle));
        verify(userService).saveDealership(testUser);
    }
}
