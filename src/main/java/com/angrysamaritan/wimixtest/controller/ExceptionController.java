package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.dto.ErrorsDto;
import com.angrysamaritan.wimixtest.exceptions.*;
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


    @ExceptionHandler({SignUpException.class, ProfileRequestException.class})
    public ResponseEntity<ErrorsDto> requestFormatException(RequestFormatException exception) {
        ErrorsDto errorsDto = errorsUtil.processErrors(exception.getErrors());
        errorsDto.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorsDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoEmailSetException.class)
    public ResponseEntity<String> noEmailSetException(NoEmailSetException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ProfileNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<String> userNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
