package com.renohome.generic.expcetions;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String detail) {
        super(String.format("Internal Server error: %s.", detail));
    }
}
