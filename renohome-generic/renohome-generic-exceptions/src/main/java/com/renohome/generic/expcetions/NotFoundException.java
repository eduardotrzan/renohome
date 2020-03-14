package com.renohome.generic.expcetions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String detail) {
        super(String.format("Not found object: %s.", detail));
    }
}
