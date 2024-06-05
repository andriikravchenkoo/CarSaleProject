package com.andriikravchenkoo.carsaleproject.controller.advice;

import com.andriikravchenkoo.carsaleproject.dto.DealershipCreateDto;
import com.andriikravchenkoo.carsaleproject.dto.RegisterRequestDto;
import com.andriikravchenkoo.carsaleproject.dto.VehicleAnnouncementCreateDto;
import com.andriikravchenkoo.carsaleproject.exception.DataAlreadyExistsException;
import com.andriikravchenkoo.carsaleproject.exception.ImageCompressException;
import com.andriikravchenkoo.carsaleproject.exception.ImageNotSavedException;
import com.andriikravchenkoo.carsaleproject.exception.ResourceNotFoundException;
import com.andriikravchenkoo.carsaleproject.model.enums.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", HttpStatus.NOT_FOUND);
        modelAndView.addObject("timestamp", new Date());
        modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ModelAndView handleDataAlreadyExistsException(
            DataAlreadyExistsException exception, WebRequest webRequest) {
        final String requestUrl = getRequestUrl(webRequest);
        ModelAndView modelAndView;
        if (requestUrl.contains("/authentication/register")) {
            modelAndView = new ModelAndView("authentication/register");
            modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
            modelAndView.addObject("registerRequestDto", new RegisterRequestDto());
            modelAndView.addObject("roles", Role.values());
            return modelAndView;
        } else if (requestUrl.contains("/dealership/create")) {
            modelAndView = new ModelAndView("dealership/create");
            modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
            modelAndView.addObject("dealershipDto", new DealershipCreateDto());
            modelAndView.addObject("regions", Region.values());
            return modelAndView;
        } else if (requestUrl.contains("/announcement/create")) {
            modelAndView = new ModelAndView("announcement/create");
            modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
            modelAndView.addObject("vehicleAnnouncementDto", new VehicleAnnouncementCreateDto());
            modelAndView.addObject("bodyTypes", BodyType.values());
            modelAndView.addObject("engineTypes", EngineType.values());
            modelAndView.addObject("transmissions", Transmission.values());
            modelAndView.addObject("colors", Color.values());
            return modelAndView;
        } else {
            modelAndView = new ModelAndView("error");
            modelAndView.addObject("statusCode", HttpStatus.CONFLICT);
            modelAndView.addObject("timestamp", new Date());
            modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
            return modelAndView;
        }
    }

    @ExceptionHandler(ImageNotSavedException.class)
    public ModelAndView handleImageNotSavedException(
            ImageNotSavedException exception, WebRequest webRequest) {
        final String requestUrl = getRequestUrl(webRequest);
        ModelAndView modelAndView;
        if (requestUrl.contains("/authentication/register")) {
            modelAndView = new ModelAndView("authentication/register");
            modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
            modelAndView.addObject("registerRequestDto", new RegisterRequestDto());
            modelAndView.addObject("roles", Role.values());
            return modelAndView;
        } else if (requestUrl.contains("/dealership/create")) {
            modelAndView = new ModelAndView("dealership/create");
            modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
            modelAndView.addObject("dealershipDto", new DealershipCreateDto());
            modelAndView.addObject("regions", Region.values());
            return modelAndView;
        } else if (requestUrl.contains("/announcement/create")) {
            modelAndView = new ModelAndView("announcement/create");
            modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
            modelAndView.addObject("vehicleAnnouncementDto", new VehicleAnnouncementCreateDto());
            modelAndView.addObject("bodyTypes", BodyType.values());
            modelAndView.addObject("engineTypes", EngineType.values());
            modelAndView.addObject("transmissions", Transmission.values());
            modelAndView.addObject("colors", Color.values());
            return modelAndView;
        } else {
            modelAndView = new ModelAndView("error");
            modelAndView.addObject("statusCode", HttpStatus.BAD_REQUEST);
            modelAndView.addObject("timestamp", new Date());
            modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
            return modelAndView;
        }
    }

    @ExceptionHandler(ImageCompressException.class)
    public ModelAndView handleImageCompressException(ImageCompressException exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", HttpStatus.BAD_REQUEST);
        modelAndView.addObject("timestamp", new Date());
        modelAndView.addObject(ERROR_MESSAGE, exception.getMessage());
        return modelAndView;
    }

    private String getRequestUrl(WebRequest webRequest) {
        return webRequest.getDescription(false);
    }
}
