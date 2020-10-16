package com.angrysamaritan.wimixtest.exceptions;

import org.springframework.validation.Errors;

public class SignUpException extends RequestFormatException {

    public SignUpException(Errors errors) {
        super(errors, "Sign up credentials validation failed");
    }
}
