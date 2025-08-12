package com.andriikravchenkoo.carsaleproject.controller;

import com.andriikravchenkoo.carsaleproject.dto.DealershipCreateDto;
import com.andriikravchenkoo.carsaleproject.dto.DealershipPageDto;
import com.andriikravchenkoo.carsaleproject.facade.DealershipServiceFacade;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.model.enums.Region;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dealership")
public class DealershipController {

    private final DealershipServiceFacade dealershipServiceFacade;

    @Value("${limit.per.page}")
    private Long limitPerPage;

    @GetMapping("/create")
    public String getCreateDealershipPage(Model model) {
        model.addAttribute("dealershipCreateDto", new DealershipCreateDto());
        model.addAttribute("regions", Region.values());
        return "dealership/create";
    }

    @PostMapping("/create")
    public String postCreateDealershipPage(
            @Valid DealershipCreateDto dealershipCreateDto,
            BindingResult bindingResult,
            List<MultipartFile> files,
            @AuthenticationPrincipal User user,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("regions", Region.values());
            return "dealership/create";
        }

        Long createdDealershipId =
                dealershipServiceFacade.createDealership(dealershipCreateDto, files, user);

        return "redirect:/dealership/" + createdDealershipId;
    }

    @GetMapping("/{id}")
    public String getDealershipPage(
            @PathVariable Long id, Model model, @AuthenticationPrincipal User authenticationUser) {
        DealershipPageDto dealership =
                dealershipServiceFacade.getDealershipWithImages(id, authenticationUser);
        model.addAttribute("images", dealership.getImages());
        model.addAttribute("dealership", dealership);
        return "dealership/dealership";
    }

    @GetMapping("/page")
    public String getAllDealershipsByDateForPage(@RequestParam Long pageId, Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<DealershipPageDto> dealerships =
                dealershipServiceFacade.getAllDealershipsByDate(limitPerPage, offset);

        long totalCountDealerships = dealershipServiceFacade.getTotalCountDealerships();
        long totalPages = (long) Math.ceil((double) totalCountDealerships / limitPerPage);

        model.addAttribute("dealerships", dealerships);
        model.addAttribute("pageId", pageId);
        model.addAttribute("totalPages", totalPages);

        return "dealership/dealerships";
    }

    @GetMapping("/search")
    public String searchDealerships(
            @RequestParam Long pageId,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String region,
            Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<DealershipPageDto> dealerships =
                dealershipServiceFacade.searchDealerships(limitPerPage, offset, query, region);

        long totalCountDealerships = dealershipServiceFacade.getTotalCountDealershipsByFilter(query, region);
        long totalPages = (long) Math.ceil((double) totalCountDealerships / limitPerPage);

        model.addAttribute("dealerships", dealerships);
        model.addAttribute("pageId", pageId);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("query", query);
        model.addAttribute("region", region);
        return "dealership/dealerships";
    }

    @PostMapping("/add-seller")
    public String postBecomingSellerInDealership(
            @RequestParam("dealershipId") Long dealershipId,
            @AuthenticationPrincipal User authenticationUser) {
        dealershipServiceFacade.becomeSeller(dealershipId, authenticationUser);
        return "redirect:/dealership/" + dealershipId;
    }
}
