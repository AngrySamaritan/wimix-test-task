package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.model.UserDto;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Objects;

@Service
public class SignUpServiceImpl implements SignUpService {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User processDto(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(encoder.encode(userDto.getPassword()));
        return user;
    }

    public JSONObject processErrors(Errors errors) throws JSONException {
        JSONObject errorsJson = new JSONObject();
        if (errors.getFieldError("email") != null) {
            errorsJson.put("email", Objects.requireNonNull(errors.getFieldError("email")).getDefaultMessage());
        }
        if (errors.getFieldError("password") != null) {
            errorsJson.put("password", Objects.requireNonNull(errors.getFieldError("password")).getDefaultMessage());
        }
        return errorsJson;
    }
}
