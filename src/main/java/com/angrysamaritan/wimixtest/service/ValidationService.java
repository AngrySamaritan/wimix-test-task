package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.UserDto;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class ValidationService {

    public JSONObject processErrors(Set<ConstraintViolation<UserDto>> errors) throws Exception {
        JSONObject errorsJson = new JSONObject();
        for (var error: errors) {
            String fieldName = String.valueOf(error.getPropertyPath());
            errorsJson.put(fieldName.length() != 0 ? fieldName : "global", error.getMessage());
        }
        return errorsJson;
    }

//    @Bean
//    public Validator getValidator() {
//        return Validation.buildDefaultValidatorFactory().getValidator();
//    }
}
