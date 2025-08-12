package com.andriikravchenkoo.carsaleproject.facade.impl;

import com.andriikravchenkoo.carsaleproject.dto.DealershipCreateDto;
import com.andriikravchenkoo.carsaleproject.dto.DealershipPageDto;
import com.andriikravchenkoo.carsaleproject.facade.DealershipServiceFacade;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.model.entity.Vehicle;
import com.andriikravchenkoo.carsaleproject.service.DealershipService;
import com.andriikravchenkoo.carsaleproject.service.ImageService;
import com.andriikravchenkoo.carsaleproject.service.UserService;
import com.andriikravchenkoo.carsaleproject.service.VehicleService;

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

    private final VehicleService vehicleService;

    @Override
    public List<DealershipPageDto> getAllDealershipsByDate(Long limitPerPage, Long offset) {
        return dealershipService.findAllByDate(limitPerPage, offset).stream()
                .map(this::mapToDealershipPageDto)
                .toList();
    }

    private DealershipPageDto mapToDealershipPageDto(Dealership dealership) {
        List<Image> images = imageService.findAllByDealershipId(dealership.getId());
        Long countOfVehiclesInDealership =
                vehicleService.findCountByDealershipId(dealership.getId());

        return new DealershipPageDto(
                dealership.getId(),
                dealership.getName(),
                dealership.getRegion(),
                images,
                countOfVehiclesInDealership);
    }

    @Override
    public Long getTotalCountDealerships() {
        return dealershipService.findTotalCount();
    }

    @Override
    @Transactional
    public Long createDealership(
            DealershipCreateDto dealershipCreateDto, List<MultipartFile> files, User user) {
        Dealership savedDealership = dealershipService.save(dealershipCreateDto.toEntity());

        List<Image> images = imageService.saveAll(files);

        savedDealership.setImages(images);

        imageService.saveAllDealershipImages(savedDealership);

        user.setDealership(savedDealership);

        userService.saveDealership(user);

        return savedDealership.getId();
    }

    @Override
    public DealershipPageDto getDealershipWithImages(Long id, User authenticationUser) {
        List<Image> images = imageService.findAllByDealershipId(id);

        Dealership dealership = dealershipService.findById(id);

        Boolean isSeller =
                userService.checkIsSellerInDealership(
                        authenticationUser.getEmail(), dealership.getId());

        return dealership.toDto(images, isSeller);
    }

    @Override
    @Transactional
    public void becomeSeller(Long dealershipId, User user) {
        List<Vehicle> vehicles = vehicleService.findAllByUserId(user.getId());

        Dealership dealership = dealershipService.findById(dealershipId);

        List<Vehicle> savedVehicles =
                vehicles.stream().peek(vehicle -> vehicle.setDealership(dealership)).toList();

        vehicleService.updateAllWithNewDealerships(savedVehicles);

        user.setDealership(dealership);

        userService.saveDealership(user);
    }

    @Override
    public List<DealershipPageDto> searchDealerships(
            Long limitPerPage, Long offset, String query, String region) {
        return dealershipService.findAllByFilter(limitPerPage, offset, query, region).stream()
                .map(this::mapToDealershipPageDto)
                .toList();
    }

    @Override
    public Long getTotalCountDealershipsByFilter(String query, String region) {
        return dealershipService.findTotalCountByFilter(query, region);
    }
}
