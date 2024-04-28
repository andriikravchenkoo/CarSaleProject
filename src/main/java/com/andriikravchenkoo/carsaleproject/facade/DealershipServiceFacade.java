package com.andriikravchenkoo.carsaleproject.facade;

import com.andriikravchenkoo.carsaleproject.dto.DealershipCreateDto;
import com.andriikravchenkoo.carsaleproject.dto.DealershipPageDto;
import com.andriikravchenkoo.carsaleproject.model.entity.User;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DealershipServiceFacade {

    List<DealershipPageDto> getAllDealershipsByDate(Long limitPerPage, Long offset);

    Long getTotalCountDealerships();

    void createDealership(
            DealershipCreateDto dealershipCreateDto, List<MultipartFile> files, User user);

    DealershipPageDto getDealershipWithImages(Long id);

    void becomeSeller(Long dealershipId, User user);
}
