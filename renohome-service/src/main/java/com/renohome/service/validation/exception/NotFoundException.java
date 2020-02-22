package com.renohome.service.validation.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String detail) {
        super(String.format("Not found object: %s.", detail));
    }
}
