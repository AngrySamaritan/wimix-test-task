package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.model.UserDto;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Service
public interface SignUpService {

    User signIn(UserDto userDto);

}
