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
        List<Dealership> dealerships = dealershipService.findAllByDateForPage(limitPerPage, offset);

        return dealerships.stream()
                .map(
                        dealership -> {
                            List<Image> images =
                                    imageService.findAllByDealershipId(dealership.getId());
                            Long countOfVehiclesInDealership =
                                    vehicleService.findCountByDealershipId(dealership.getId());

                            DealershipPageDto dealershipPageDto = new DealershipPageDto();
                            dealershipPageDto.setId(dealership.getId());
                            dealershipPageDto.setName(dealership.getName());
                            dealershipPageDto.setRegion(dealership.getRegion());
                            dealershipPageDto.setImages(images);
                            dealershipPageDto.setCountVehicles(countOfVehiclesInDealership);

                            return dealershipPageDto;
                        })
                .toList();
    }

    @Override
    public Long getTotalCountDealerships() {
        return dealershipService.findTotalCount();
    }

    @Override
    @Transactional
    public void createDealership(
            DealershipCreateDto dealershipCreateDto, List<MultipartFile> files, User user) {
        Dealership dealership = dealershipService.save(dealershipCreateDto.toEntity());

        List<Image> images = imageService.saveAll(files);

        dealership.setImages(images);

        imageService.saveAllDealershipImages(dealership);

        user.setDealership(dealership);

        userService.saveDealership(user);
    }

    @Override
    public DealershipPageDto getDealershipWithImages(Long id) {
        List<Image> images = imageService.findAllByDealershipId(id);

        return dealershipService.findById(id).toDto(images);
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
}
