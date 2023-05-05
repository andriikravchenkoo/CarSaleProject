package com.andriikravchenkoo.carsaleproject.controller;

import com.andriikravchenkoo.carsaleproject.dto.RegisterRequestDto;

import com.andriikravchenkoo.carsaleproject.model.enums.Role;
import com.andriikravchenkoo.carsaleproject.security.facade.RegistrationServiceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {

    private final RegistrationServiceFacade registrationServiceFacade;

    @GetMapping("/login")
    public String getLoginPage() {
        return "authentication/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequestDto", new RegisterRequestDto());
        model.addAttribute("roles", Role.values());
        return "authentication/register";
    }

    @PostMapping("/register")
    public String getExecuteRegisterPage(@Valid RegisterRequestDto registerRequestDto, BindingResult bindingResult, MultipartFile file, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "authentication/register";
        }

        registrationServiceFacade.register(registerRequestDto, file);

        return "redirect:/authentication/login";
    }
}
