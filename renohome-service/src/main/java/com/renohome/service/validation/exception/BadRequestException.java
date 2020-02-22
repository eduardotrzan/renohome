package com.renohome.service.validation.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String detail) {
        super(String.format("Bad request object: %s.", detail));
    }
}
