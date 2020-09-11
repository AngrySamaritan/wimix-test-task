package com.angrysamaritan.wimixtest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlankProfileFieldException extends RuntimeException {
    public BlankProfileFieldException(String message) {
        super(message);
    }
}
