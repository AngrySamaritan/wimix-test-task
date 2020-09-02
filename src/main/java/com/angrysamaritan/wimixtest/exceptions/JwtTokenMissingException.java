package com.angrysamaritan.wimixtest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtTokenMissingException extends RuntimeException {
    public JwtTokenMissingException(String s) {
        super(s);
    }
}
