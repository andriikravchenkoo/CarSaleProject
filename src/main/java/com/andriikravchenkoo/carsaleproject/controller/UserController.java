package com.andriikravchenkoo.carsaleproject.controller;

import com.andriikravchenkoo.carsaleproject.dto.UserProfilePageDto;
import com.andriikravchenkoo.carsaleproject.facade.UserServiceFacade;
import com.andriikravchenkoo.carsaleproject.model.entity.User;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserServiceFacade userServiceFacade;

    @GetMapping("/profile")
    public String getUserProfilePage(@AuthenticationPrincipal User authenticationUser, Model model)
            throws IOException {
        UserProfilePageDto user = userServiceFacade.getUserWithImage(authenticationUser);
        model.addAttribute("user", user);
        model.addAttribute("image", user.getImage());
        return "user/profile";
    }
}
