package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.dto.LogInReq;
import com.angrysamaritan.wimixtest.dto.SignUpReq;
import com.angrysamaritan.wimixtest.exceptions.SignUpException;
import com.angrysamaritan.wimixtest.service.implementations.UserDetailsServiceImpl;
import com.angrysamaritan.wimixtest.service.JWTService;
import com.angrysamaritan.wimixtest.service.SignUpService;
import io.swagger.annotations.Api;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final SignUpService signUpService;

    public UserController(AuthenticationManager authenticationManager, JWTService jwtService,
                          UserDetailsServiceImpl userDetailsService, SignUpService signUpService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.signUpService = signUpService;
    }

    @PostMapping("/login")
    public Object login(@ModelAttribute LogInReq authenticationRequest) {
        String username = authenticationRequest.getUsername();
        authenticate(username, authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtService.generateToken(userDetails);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
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