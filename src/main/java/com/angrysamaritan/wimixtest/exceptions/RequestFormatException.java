package com.angrysamaritan.wimixtest.exceptions;

import lombok.Getter;
import org.springframework.validation.Errors;

public class RequestFormatException extends RuntimeException {

    @Getter
    private final Errors errors;

    public RequestFormatException(Errors errors, String message) {
        super(message);
        this.errors = errors;
    }


}
