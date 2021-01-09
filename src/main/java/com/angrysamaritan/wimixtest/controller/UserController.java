package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.dto.SignUpReq;
import com.angrysamaritan.wimixtest.exceptions.SignUpException;
import com.angrysamaritan.wimixtest.service.JWTService;
import com.angrysamaritan.wimixtest.service.SignUpService;
import com.angrysamaritan.wimixtest.service.implementations.UserDetailsServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@Api
public class UserController {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final SignUpService signUpService;

    public UserController(JWTService jwtService, UserDetailsServiceImpl userDetailsService,
                          SignUpService signUpService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.signUpService = signUpService;
    }

    @PostMapping("/login")
    public Object login(Principal principal) {
        String username = principal.getName();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtService.generateToken(userDetails);
    }

    @PostMapping("/sign_up")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Long signUp(@ModelAttribute @Valid SignUpReq userDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new SignUpException(errors);
        } else {
            return signUpService.signUp(userDto);
        }
    }
}