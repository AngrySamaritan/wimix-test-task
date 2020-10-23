package com.angrysamaritan.wimixtest.utils;

import com.angrysamaritan.wimixtest.dto.ErrorsDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Objects;

@Service
public class ErrorsUtil {

    public ErrorsDto processErrors(Errors errors) {
        var errorsDto = new ErrorsDto();
        for (var error : errors.getFieldErrors()) {
            String fieldName = error.getField();
            errorsDto.getFieldErrors().put(fieldName, error.getDefaultMessage());
        }
        if (errors.hasGlobalErrors()) {
            errorsDto.setGlobalError(Objects.requireNonNull(errors.getGlobalError()).getDefaultMessage());
        }
        return errorsDto;
    }

}
