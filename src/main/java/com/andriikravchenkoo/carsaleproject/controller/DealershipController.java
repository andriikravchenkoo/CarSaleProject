package com.andriikravchenkoo.carsaleproject.controller;

import com.andriikravchenkoo.carsaleproject.dto.DealershipDto;
import com.andriikravchenkoo.carsaleproject.facade.DealershipServiceFacade;
import com.andriikravchenkoo.carsaleproject.model.entity.Dealership;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.model.enums.Region;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dealership")
public class DealershipController {

    private final DealershipServiceFacade dealershipServiceFacade;

    @GetMapping("/create")
    public String getCreateDealershipPage(Model model) {
        model.addAttribute("dealershipDto", new DealershipDto());
        model.addAttribute("regions", Region.values());
        return "dealership/create";
    }

    @PostMapping("/create")
    public String postCreateDealershipPage(
            @Valid DealershipDto dealershipDto,
            BindingResult bindingResult,
            List<MultipartFile> files,
            @AuthenticationPrincipal User user,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("regions", Region.values());
            return "dealership/create";
        }

        dealershipServiceFacade.createDealership(dealershipDto, files, user);

        return "redirect:/vehicle/home";
    }

    @GetMapping("/{id}")
    public String getDealershipPage(@PathVariable Long id, Model model) {
        Dealership dealership = dealershipServiceFacade.getDealershipWithImages(id);
        model.addAttribute("images", dealership.getImages());
        model.addAttribute("dealership", dealership);
        return "dealership/dealership";
    }

    @PostMapping("/add-seller")
    public String postBecomingSellerInDealership(
            @RequestParam("dealershipId") Long dealershipId, @AuthenticationPrincipal User user) {
        dealershipServiceFacade.becomeSeller(dealershipId, user);
        return "redirect:/dealership/" + dealershipId;
    }
}
