package com.angrysamaritan.wimixtest.exceptions;

import org.springframework.validation.Errors;

public class ProfileRequestException extends RequestFormatException {

    public ProfileRequestException(Errors errors) {
        super(errors, "Wrong operation credentials");
    }
}
