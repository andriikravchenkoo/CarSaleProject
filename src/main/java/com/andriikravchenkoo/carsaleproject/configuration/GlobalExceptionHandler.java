package com.andriikravchenkoo.carsaleproject.configuration;

import com.andriikravchenkoo.carsaleproject.dto.RegisterRequestDto;
import com.andriikravchenkoo.carsaleproject.model.enums.Role;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("timestamp", new Date());
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ModelAndView handleDuplicateKeyException() {
        ModelAndView modelAndView = new ModelAndView("authentication/register");
        modelAndView.addObject("errorMessage", "Email or phone number already exists");
        modelAndView.addObject("registerRequestDto", new RegisterRequestDto());
        modelAndView.addObject("roles", Role.values());
        return modelAndView;
    }
}
