package com.angrysamaritan.wimixtest.control;

import com.angrysamaritan.wimixtest.model.UserDto;
import com.angrysamaritan.wimixtest.repos.UserRepo;
import com.angrysamaritan.wimixtest.service.SignUpService;
import org.hibernate.HibernateException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class SignUpController {

    private final SignUpService signUpService;
    private final UserRepo userRepo;

    public SignUpController(SignUpService signUpService, UserRepo userRepo) {
        this.signUpService = signUpService;
        this.userRepo = userRepo;
    }

    @GetMapping("/sign_up")
    public String users(@ModelAttribute @Valid UserDto userDto, Errors errors, HttpServletRequest request) throws JSONException {
        JSONObject response = new JSONObject();
        if (errors.hasErrors()) {
            response.put("errors", signUpService.processErrors(errors));
        } else {
            try {
                userRepo.save(signUpService.processDto(userDto));
            } catch (HibernateException e) {
                response.put("errors", new JSONObject().put("username", "Username exist"));
            }
        }
        response.put("request", request);
        return null;
    }
}
