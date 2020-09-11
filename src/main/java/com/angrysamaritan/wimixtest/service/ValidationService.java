package com.angrysamaritan.wimixtest.service;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Service
public class ValidationService {

    public JSONObject processErrors(Set<? extends ConstraintViolation<?>> errors) throws Exception {
        JSONObject errorsJson = new JSONObject();
        for (var error: errors) {
            String fieldName = String.valueOf(error.getPropertyPath());
            errorsJson.put(fieldName.length() != 0 ? fieldName : "global", new JSONArray().put(error.getMessage()));
        }
        return errorsJson;
    }

//    @Bean
//    public Validator getValidator() {
//        return Validation.buildDefaultValidatorFactory().getValidator();
//    }
}
