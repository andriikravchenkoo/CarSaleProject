package com.andriikravchenkoo.carsaleproject.facade.impl;

import com.andriikravchenkoo.carsaleproject.dto.DealershipDto;
import com.andriikravchenkoo.carsaleproject.facade.DealershipServiceFacade;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.model.entity.Image;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.model.entity.Vehicle;
import com.andriikravchenkoo.carsaleproject.service.DealershipService;
import com.andriikravchenkoo.carsaleproject.service.ImageService;
import com.andriikravchenkoo.carsaleproject.service.UserService;
import com.andriikravchenkoo.carsaleproject.service.VehicleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DealershipServiceFacadeImpl implements DealershipServiceFacade {

  private final DealershipService dealershipService;

  private final ImageService imageService;

  private final UserService userService;

  private final VehicleService vehicleService;

  @Override
  @Transactional
  public void createDealership(DealershipDto dealershipDto, List<MultipartFile> files, User user) {
    Dealership dealership = dealershipService.save(dealershipDto.toEntity());

    List<Image> images = imageService.saveAll(files);

    dealership.setImages(images);

    imageService.saveAllDealershipImages(dealership);

    user.setDealership(dealership);

    userService.saveDealership(user);
  }

  @Override
  public Dealership getDealershipWithImages(Long id) {
    List<Image> images = imageService.findAllByDealershipId(id);

    Dealership dealership = dealershipService.findById(id);

    dealership.setImages(images);

    return dealership;
  }

  @Override
  @Transactional
  public void becomeSeller(Long dealershipId, User user) {
    List<Vehicle> vehicles = vehicleService.findAllByUserId(user.getId());

    Dealership dealership = dealershipService.findById(dealershipId);

    vehicles.stream().peek(vehicle -> vehicle.setDealership(dealership)).toList();

    vehicleService.updateAllWithNewDealerships(vehicles);

    user.setDealership(dealership);

    userService.saveDealership(user);
  }
}
