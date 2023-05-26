package com.andriikravchenkoo.carsaleproject.controller;

import com.andriikravchenkoo.carsaleproject.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vehicle")
public class VehicleController {

  private final VehicleService vehicleService;

  @GetMapping("/home")
  public String getAllVehicles(Model model) {
    model.addAttribute("vehicles", vehicleService.findAll());
    return "home";
  }
}
