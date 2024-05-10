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
        addVehicleAttributes(model);
        return "announcement/create";
    }

    @PostMapping("/create")
    public String postCreateAnnouncementPage(
            @Valid VehicleAnnouncementCreateDto vehicleAnnouncementCreateDto,
            BindingResult bindingResult,
            List<MultipartFile> files,
            @AuthenticationPrincipal User authenticatioUser,
            Model model) {
        if (bindingResult.hasErrors()) {
            addVehicleAttributes(model);
            return "announcement/create";
        }

        Long createdAnnouncementId =
                announcementServiceFacade.createAnnouncement(
                        vehicleAnnouncementCreateDto, files, authenticatioUser);
        return "redirect:/announcement/" + createdAnnouncementId;
    }

    private void addVehicleAttributes(Model model) {
        model.addAttribute("bodyTypes", BodyType.values());
        model.addAttribute("engineTypes", EngineType.values());
        model.addAttribute("transmissions", Transmission.values());
        model.addAttribute("colors", Color.values());
    }

    @GetMapping("/{id}")
    public String getAnnouncementPage(
            @PathVariable Long id, @AuthenticationPrincipal User authenticationUser, Model model)
            throws IOException {
        AnnouncementPageDto announcement =
                announcementServiceFacade.getAnnouncementWithImages(id, authenticationUser.getId());
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

        model.addAttribute("announcements", announcements);
        setupPaginationModel(model, limitPerPage, totalCountAnnouncements, pageId);

        return "announcement/announcements";
    }

    @GetMapping("/my/page")
    public String getAllAnnouncementsForSellerForPage(
            @RequestParam Long pageId,
            @AuthenticationPrincipal User authenticationUser,
            Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<AnnouncementPageDto> announcements =
                announcementServiceFacade.getAllAnnouncementByUser(
                        limitPerPage, offset, authenticationUser.getId());

        long totalCountAnnouncements =
                announcementServiceFacade.getTotalCountAnnouncementByUser(
                        authenticationUser.getId());

        model.addAttribute("announcements", announcements);
        setupPaginationModel(model, limitPerPage, totalCountAnnouncements, pageId);

        return "announcement/owner-announcements";
    }

    @GetMapping("/by-seller/{sellerId}/page")
    public String getAllAnnouncementsBySellerForUsersForPage(
            @RequestParam Long pageId, @PathVariable Long sellerId, Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<AnnouncementPageDto> announcements =
                announcementServiceFacade.getAllAnnouncementByUser(limitPerPage, offset, sellerId);

        long totalCountAnnouncements =
                announcementServiceFacade.getTotalCountAnnouncementByUser(sellerId);

        model.addAttribute("announcements", announcements);
        setupPaginationModel(model, limitPerPage, totalCountAnnouncements, pageId);

        return "announcement/seller-announcements";
    }

    @GetMapping("/my-favorites/page")
    public String getAllFavoritesAnnouncementsByUserForPage(
            @RequestParam Long pageId,
            @AuthenticationPrincipal User authenticationUser,
            Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<AnnouncementPageDto> announcements =
                announcementServiceFacade.getAllFavoritesAnnouncementsByUser(
                        limitPerPage, offset, authenticationUser.getId());

        long totalCountAnnouncements =
                announcementServiceFacade.getTotalCountFavoritesAnnouncementByUser(
                        authenticationUser.getId());

        model.addAttribute("announcements", announcements);
        setupPaginationModel(model, limitPerPage, totalCountAnnouncements, pageId);

        return "announcement/user-favorites-announcements";
    }

    @GetMapping("/new-vehicles/page")
    public String getAllAnnouncementsByNewVehicleForPage(@RequestParam Long pageId, Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<AnnouncementPageDto> announcements =
                announcementServiceFacade.getAllAnnouncementByVehicleUsage(
                        limitPerPage, offset, false);

        long totalCountAnnouncements =
                announcementServiceFacade.getTotalCountAnnouncementByVehicleUsage(false);

        model.addAttribute("announcements", announcements);
        setupPaginationModel(model, limitPerPage, totalCountAnnouncements, pageId);

        return "announcement/new-vehicle-announcements";
    }

    @GetMapping("/used-vehicles/page")
    public String getAllAnnouncementsByUsedVehicleForPage(@RequestParam Long pageId, Model model) {
        long offset = (pageId - 1) * limitPerPage;

        List<AnnouncementPageDto> announcements =
                announcementServiceFacade.getAllAnnouncementByVehicleUsage(
                        limitPerPage, offset, true);

        long totalCountAnnouncements =
                announcementServiceFacade.getTotalCountAnnouncementByVehicleUsage(true);

        model.addAttribute("announcements", announcements);
        setupPaginationModel(model, limitPerPage, totalCountAnnouncements, pageId);

        return "announcement/used-vehicle-announcements";
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

        model.addAttribute("announcements", announcements);
        setupPaginationModel(model, limitPerPage, totalCountAnnouncements, pageId);

        return "announcement/announcements-by-dealership";
    }

    private void setupPaginationModel(
            Model model, long limitPerPage, long totalItems, long pageId) {
        long totalPages = (long) Math.ceil((double) totalItems / limitPerPage);
        model.addAttribute("pageId", pageId);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("hasAnnouncements", totalItems > 0);
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
        return "redirect:/announcement/my/page?pageId=1";
    }
}