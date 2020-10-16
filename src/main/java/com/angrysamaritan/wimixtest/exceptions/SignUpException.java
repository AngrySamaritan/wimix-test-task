package com.angrysamaritan.wimixtest.exceptions;

import lombok.Getter;
import org.springframework.validation.Errors;

public class SignUpException extends RuntimeException {

    @Getter
    private final Errors errors;

    public SignUpException(Errors errors) {
        super("Sign up error occurred!");
        this.errors = errors;
    }
}
