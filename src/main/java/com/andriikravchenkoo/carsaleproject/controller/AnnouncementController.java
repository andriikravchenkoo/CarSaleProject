package com.andriikravchenkoo.carsaleproject.controller;

import com.andriikravchenkoo.carsaleproject.dto.AnnouncementPageDto;
import com.andriikravchenkoo.carsaleproject.dto.VehicleAnnouncementCreateDto;
import com.andriikravchenkoo.carsaleproject.facade.AnnouncementServiceFacade;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.model.enums.*;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementServiceFacade announcementServiceFacade;

    @Value("${limit.per.page}")
    private Long limitPerPage;

    @GetMapping("/create")
    public String getCreateAnnouncementPage(Model model) {
        model.addAttribute("vehicleAnnouncementCreateDto", new VehicleAnnouncementCreateDto());
        model.addAttribute("bodyTypes", BodyType.values());
        model.addAttribute("engineTypes", EngineType.values());
        model.addAttribute("transmissions", Transmission.values());
        model.addAttribute("colors", Color.values());
        return "announcement/create";
    }

    @PostMapping("/create")
    public String postCreateAnnouncementPage(
            @Valid VehicleAnnouncementCreateDto vehicleAnnouncementCreateDto,
            BindingResult bindingResult,
            List<MultipartFile> files,
            @AuthenticationPrincipal User user,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bodyTypes", BodyType.values());
            model.addAttribute("engineTypes", EngineType.values());
            model.addAttribute("transmissions", Transmission.values());
            model.addAttribute("colors", Color.values());
            return "announcement/create";
        }

        Long createdAnnouncementId =
                announcementServiceFacade.createAnnouncement(
                        vehicleAnnouncementCreateDto, files, user);

        return "redirect:/announcement/" + createdAnnouncementId;
    }

    @GetMapping("/{id}")
    public String getAnnouncementPage(
            @PathVariable Long id, @AuthenticationPrincipal User authenticationUser, Model model)
            throws IOException {
        AnnouncementPageDto announcement =
                announcementServiceFacade.getAnnouncementWithImages(id, authenticationUser);
        model.addAttribute("images", announcement.getImages());
        model.addAttribute("dealership", announcement.getUser().getDealership());
        model.addAttribute("user", announcement.getUser());
        model.addAttribute("image", announcement.getUser().getImage());
        model.addAttribute("vehicle", announcement.getVehicle());
        model.addAttribute("announcement", announcement);
        return "announcement/announcement";
    }

    @GetMapping("/page")
    public String getAllAnnouncementsByDateForPage(@RequestParam Long pageId, Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<AnnouncementPageDto> announcements =
                announcementServiceFacade.getAllAnnouncementsByDate(limitPerPage, offset);

        long totalCountAnnouncements = announcementServiceFacade.getTotalCountAnnouncements();
        long totalPages = (long) Math.ceil((double) totalCountAnnouncements / limitPerPage);

        model.addAttribute("announcements", announcements);
        model.addAttribute("pageId", pageId);
        model.addAttribute("totalPages", totalPages);

        return "announcement/announcements";
    }

    @GetMapping("/my/page")
    public String getAllAnnouncementsByUserForPage(
            @RequestParam Long pageId,
            @AuthenticationPrincipal User authenticationUser,
            Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<AnnouncementPageDto> announcements =
                announcementServiceFacade.getAllAnnouncementByUser(
                        limitPerPage, offset, authenticationUser);

        long totalCountAnnouncements =
                announcementServiceFacade.getTotalCountAnnouncementByUser(
                        authenticationUser.getId());
        long totalPages = (long) Math.ceil((double) totalCountAnnouncements / limitPerPage);

        model.addAttribute("announcements", announcements);
        model.addAttribute("pageId", pageId);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("hasAnnouncements", totalCountAnnouncements > 0);

        return "announcement/user_announcements";
    }

    @GetMapping("/my-favorites/page")
    public String getAllFavoritesAnnouncementsByUserForPage(
            @RequestParam Long pageId,
            @AuthenticationPrincipal User authenticationUser,
            Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<AnnouncementPageDto> announcements =
                announcementServiceFacade.getAllFavoritesAnnouncementsByUser(
                        limitPerPage, offset, authenticationUser);

        long totalCountAnnouncements =
                announcementServiceFacade.getTotalCountFavoritesAnnouncementByUser(
                        authenticationUser.getId());
        long totalPages = (long) Math.ceil((double) totalCountAnnouncements / limitPerPage);

        model.addAttribute("announcements", announcements);
        model.addAttribute("pageId", pageId);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("hasFavorites", totalCountAnnouncements > 0);

        return "announcement/user_favorites_announcements";
    }

    @GetMapping("/new-vehicles/page")
    public String getAllAnnouncementsByNewVehicleForPage(@RequestParam Long pageId, Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<AnnouncementPageDto> announcements =
                announcementServiceFacade.getAllAnnouncementByVehicleUsage(
                        limitPerPage, offset, false);

        long totalCountAnnouncements =
                announcementServiceFacade.getTotalCountAnnouncementByVehicleUsage(false);
        long totalPages = (long) Math.ceil((double) totalCountAnnouncements / limitPerPage);

        model.addAttribute("announcements", announcements);
        model.addAttribute("pageId", pageId);
        model.addAttribute("totalPages", totalPages);

        return "announcement/new_vehicle_announcements";
    }

    @GetMapping("/used-vehicles/page")
    public String getAllAnnouncementsByUsedVehicleForPage(@RequestParam Long pageId, Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<AnnouncementPageDto> announcements =
                announcementServiceFacade.getAllAnnouncementByVehicleUsage(
                        limitPerPage, offset, true);

        long totalCountAnnouncements =
                announcementServiceFacade.getTotalCountAnnouncementByVehicleUsage(true);
        long totalPages = (long) Math.ceil((double) totalCountAnnouncements / limitPerPage);

        model.addAttribute("announcements", announcements);
        model.addAttribute("pageId", pageId);
        model.addAttribute("totalPages", totalPages);

        return "announcement/used_vehicle_announcements";
    }

    @GetMapping("/by-dealership/{dealershipId}/page")
    public String getAllAnnouncementsByDealershipForPage(
            @PathVariable Long dealershipId, @RequestParam Long pageId, Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<AnnouncementPageDto> announcements =
                announcementServiceFacade.getAllAnnouncementsByDealership(
                        limitPerPage, offset, dealershipId);

        long totalCountAnnouncements =
                announcementServiceFacade.getTotalCountAnnouncementsByDealershipId(dealershipId);
        long totalPages = (long) Math.ceil((double) totalCountAnnouncements / limitPerPage);

        model.addAttribute("announcements", announcements);
        model.addAttribute("pageId", pageId);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("dealershipId", dealershipId);
        model.addAttribute("hasAnnouncements", totalCountAnnouncements > 0);

        return "announcement/announcements_by_dealership";
    }

    @PostMapping("/add-to-favorites")
    public String postAddAnnouncementToFavorites(
            @RequestParam("announcementId") Long announcementId,
            @AuthenticationPrincipal User user) {
        announcementServiceFacade.addAnnouncementToFavorites(announcementId, user.getId());
        return "redirect:/announcement/" + announcementId;
    }

    @PostMapping("/remove-from-favorites")
    public String postRemoveAnnouncementFromFavorites(
            @RequestParam("announcementId") Long announcementId,
            @AuthenticationPrincipal User user) {
        announcementServiceFacade.removeAnnouncementFromFavorites(announcementId, user.getId());
        return "redirect:/announcement/" + announcementId;
    }

    @PostMapping("/remove")
    public String postRemoveAnnouncement(@RequestParam("announcementId") Long announcementId) {
        announcementServiceFacade.deleteAnnouncement(announcementId);
        return "redirect:/vehicle/home";
    }
}
