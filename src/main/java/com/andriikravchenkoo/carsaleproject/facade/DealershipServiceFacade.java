package com.andriikravchenkoo.carsaleproject.facade;

import com.andriikravchenkoo.carsaleproject.dto.DealershipCreateDto;
import com.andriikravchenkoo.carsaleproject.dto.DealershipPageDto;
import com.andriikravchenkoo.carsaleproject.model.entity.User;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DealershipServiceFacade {

    List<DealershipPageDto> getAllDealershipsByDate(Long limitPerPage, Long offset);

    Long getTotalCountDealerships();

    Long createDealership(
            DealershipCreateDto dealershipCreateDto, List<MultipartFile> files, User user);

    DealershipPageDto getDealershipWithImages(Long id, User authenticationUser);

    void becomeSeller(Long dealershipId, User user);

    List<DealershipPageDto> searchDealerships(Long limitPerPage, Long offset, String query, String region);

    Long getTotalCountDealershipsByFilter(String query, String region);
}
