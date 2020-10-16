package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.exceptions.NoEmailSetException;
import com.angrysamaritan.wimixtest.exceptions.SignUpException;
import com.angrysamaritan.wimixtest.utils.ErrorsUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    private final ErrorsUtil errorsUtil;

    public ExceptionController(ErrorsUtil errorsUtil) {
        this.errorsUtil = errorsUtil;
    }


    @ExceptionHandler(SignUpException.class)
    public ResponseEntity<Object> signUpException(SignUpException exception) {
        var errorsDto = errorsUtil.processErrors(exception.getErrors());
        return new ResponseEntity<>(errorsDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoEmailSetException.class)
    public ResponseEntity<Object> noEmailSetException(NoEmailSetException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
