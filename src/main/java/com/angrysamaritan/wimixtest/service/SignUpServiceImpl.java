package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.model.UserDto;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@Service
public class SignUpServiceImpl implements SignUpService {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User signIn(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRegistrationDate(Date.valueOf(LocalDate.now()));
        return user;
    }
}
