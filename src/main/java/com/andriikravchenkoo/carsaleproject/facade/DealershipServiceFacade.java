package com.andriikravchenkoo.carsaleproject.facade;

import com.andriikravchenkoo.carsaleproject.dto.DealershipDto;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DealershipServiceFacade {

    void createDealership(DealershipDto dealershipDto, List<MultipartFile> files, User user);

    Dealership getDealershipWithImages(Long id);

    void becomeSeller(Long dealershipId, User user);
}
