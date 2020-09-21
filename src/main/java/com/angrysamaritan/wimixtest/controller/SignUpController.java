package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.model.UserDto;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.SignUpService;
import com.angrysamaritan.wimixtest.service.ValidationService;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@RestController
public class SignUpController {

    private final SignUpService signUpService;
    private final UserRepository userRepository;
    private final Validator validator;
    private final ValidationService validationService;

    public SignUpController(SignUpService signUpService, UserRepository userRepository, Validator validator,
                            ValidationService validationService) {
        this.signUpService = signUpService;
        this.userRepository = userRepository;
        this.validator = validator;
        this.validationService = validationService;
    }

    @PostMapping("/user.sign_up")
    public ResponseEntity<String> users(@RequestBody UserDto userDto) throws Exception {
        Set<ConstraintViolation<UserDto>> errors = validator.validate(userDto);
        var status = HttpStatus.BAD_REQUEST;
        JSONObject response = new JSONObject();
        if (errors.size() != 0) {
            response.put("errors", validationService.processErrors(errors));
        } else {
            try {
                User user = userRepository.save(signUpService.signIn(userDto));
                response.put("user_id", user.getId());
                status = HttpStatus.OK;
            } catch (DataIntegrityViolationException e) {
                response.put("errors", new JSONObject().put("username", "Username exist"));
            }
        }
        return new ResponseEntity<>(response.toString(), status);
    }
}
