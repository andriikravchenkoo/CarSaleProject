package com.andriikravchenkoo.carsaleproject.exception;

public class DataAlreadyExistsException extends RuntimeException {

    public DataAlreadyExistsException() {}

    public DataAlreadyExistsException(String message) {
        super(message);
    }
}
