package com.angrysamaritan.wimixtest.control;

import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.model.UserDto;
import com.angrysamaritan.wimixtest.repos.UserRepo;
import com.angrysamaritan.wimixtest.service.SignUpService;
import org.hibernate.HibernateException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SignUpController {

    private final SignUpService signUpService;
    private final UserRepo userRepo;

    public SignUpController(SignUpService signUpService, UserRepo userRepo) {
        this.signUpService = signUpService;
        this.userRepo = userRepo;
    }

    @PostMapping("/sign_up")
    public String users(@Valid @RequestBody UserDto userDto, Errors errors) throws Exception {
        JSONObject response = new JSONObject();
        if (errors.hasErrors()) {
            response.put("errors", signUpService.processErrors(errors));
        } else {
            try {
                User user = userRepo.save(signUpService.signIn(userDto));
                response.put("user_id", user.getId());
            } catch (HibernateException e) {
                response.put("errors", new JSONObject().put("username", "Username exist"));
            }
        }
        return response.toString();
    }
}
