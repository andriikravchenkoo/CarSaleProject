package com.andriikravchenkoo.carsaleproject.controller;

import com.andriikravchenkoo.carsaleproject.dto.VehicleAnnouncementDto;
import com.andriikravchenkoo.carsaleproject.facade.AnnouncementServiceFacade;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.model.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementServiceFacade announcementServiceFacade;

    @GetMapping("/create")
    public String getCreateAnnouncementPage(Model model) {
        model.addAttribute("vehicleAnnouncementDto", new VehicleAnnouncementDto());
        model.addAttribute("bodyTypes", BodyType.values());
        model.addAttribute("engineTypes", EngineType.values());
        model.addAttribute("transmissions", Transmission.values());
        model.addAttribute("colors", Color.values());
        return "announcement/create";
    }

    @PostMapping("/create")
    public String getExecuteAnnouncementPage(@Valid VehicleAnnouncementDto vehicleAnnouncementDto, BindingResult bindingResult, List<MultipartFile> files, @AuthenticationPrincipal User user, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bodyTypes", BodyType.values());
            model.addAttribute("engineTypes", EngineType.values());
            model.addAttribute("transmissions", Transmission.values());
            model.addAttribute("colors", Color.values());
            return "announcement/create";
        }

        announcementServiceFacade.createAnnouncement(vehicleAnnouncementDto, files, user);

        return "redirect:/vehicle/home";
    }
}
