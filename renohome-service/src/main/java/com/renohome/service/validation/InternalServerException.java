package com.renohome.service.validation;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String detail) {
        super(String.format("Internal Server error: %s.", detail));
    }
}
