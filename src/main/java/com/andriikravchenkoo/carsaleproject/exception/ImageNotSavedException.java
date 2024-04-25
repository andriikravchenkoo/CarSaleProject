package com.andriikravchenkoo.carsaleproject.exception;

public class ImageNotSavedException extends RuntimeException {

    public ImageNotSavedException() {}

    public ImageNotSavedException(String message) {
        super(message);
    }
}
