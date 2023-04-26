package com.andriikravchenkoo.carsaleproject.controller;

import com.andriikravchenkoo.carsaleproject.dto.RegisterRequestDto;
import com.andriikravchenkoo.carsaleproject.model.entity.User;
import com.andriikravchenkoo.carsaleproject.model.enums.Role;
import com.andriikravchenkoo.carsaleproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

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
    public String getExecuteRegisterPage(@Valid RegisterRequestDto registerRequestDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "authentication/register";
        }

        User user = new User(
                registerRequestDto.getFirstname(),
                registerRequestDto.getLastname(),
                registerRequestDto.getEmail(),
                passwordEncoder.encode(registerRequestDto.getPassword()),
                registerRequestDto.getPhoneNumber(),
                registerRequestDto.getRole()
        );

        userService.save(user);

        return "redirect:/authentication/login";
    }
}
