package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.model.UserDto;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public interface SignUpService {

    User signUp(UserDto userDto);

    JSONObject processErrors(Errors errors) throws Exception;
}
