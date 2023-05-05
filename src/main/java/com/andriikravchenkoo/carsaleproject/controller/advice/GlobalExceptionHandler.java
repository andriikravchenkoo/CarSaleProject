package com.andriikravchenkoo.carsaleproject.controller.advice;

import com.andriikravchenkoo.carsaleproject.dto.DealershipDto;
import com.andriikravchenkoo.carsaleproject.dto.RegisterRequestDto;
import com.andriikravchenkoo.carsaleproject.exception.DataAlreadyExistsException;
import com.andriikravchenkoo.carsaleproject.exception.ImageConvertException;
import com.andriikravchenkoo.carsaleproject.exception.ImageNotSavedException;
import com.andriikravchenkoo.carsaleproject.exception.ResourceNotFoundException;
import com.andriikravchenkoo.carsaleproject.model.enums.Region;
import com.andriikravchenkoo.carsaleproject.model.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_MESSAGE = "errorMessage";

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("timestamp", new Date());
        modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(ImageConvertException.class)
    public ModelAndView handleImageConvertException(ImageConvertException exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", HttpStatus.BAD_REQUEST);
        modelAndView.addObject("timestamp", new Date());
        modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", HttpStatus.NOT_FOUND);
        modelAndView.addObject("timestamp", new Date());
        modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ModelAndView handleDataAlreadyExistsException(DataAlreadyExistsException exception) {
        if (exception.getMessage().equals("Email or phone number already exists")) {
            ModelAndView modelAndView = new ModelAndView("authentication/register");
            modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
            modelAndView.addObject("registerRequestDto", new RegisterRequestDto());
            modelAndView.addObject("roles", Role.values());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("dealership/create");
            modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
            modelAndView.addObject("dealershipDto", new DealershipDto());
            modelAndView.addObject("regions", Region.values());
            return modelAndView;
        }
    }

    @ExceptionHandler(ImageNotSavedException.class)
    public ModelAndView handleImageNotSavedException(ImageNotSavedException exception) {
        if (exception.getMessage().equals("Upload one photo")) {
            ModelAndView modelAndView = new ModelAndView("authentication/register");
            modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
            modelAndView.addObject("registerRequestDto", new RegisterRequestDto());
            modelAndView.addObject("roles", Role.values());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("dealership/create");
            modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
            modelAndView.addObject("dealershipDto", new DealershipDto());
            modelAndView.addObject("regions", Region.values());
            return modelAndView;
        }
    }
}
