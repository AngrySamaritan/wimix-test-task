package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.DTO.ErrorsDto;
import com.angrysamaritan.wimixtest.DTO.UserDto;
import com.angrysamaritan.wimixtest.service.interfaces.JWTService;
import com.angrysamaritan.wimixtest.service.interfaces.SignUpService;
import com.angrysamaritan.wimixtest.service.implementations.UserDetailsServiceImpl;
import com.angrysamaritan.wimixtest.utils.ErrorsUtil;
import io.swagger.annotations.Api;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final SignUpService signUpService;
    private final ErrorsUtil errorsUtil;

    public UserController(AuthenticationManager authenticationManager, JWTService jwtService,
                          UserDetailsServiceImpl userDetailsService, SignUpService signUpService, ErrorsUtil errorsUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.signUpService = signUpService;
        this.errorsUtil = errorsUtil;
    }

    @PostMapping("/login")
    public Object login(@ModelAttribute UserDto.Request.Login authenticationRequest) {
        String username = authenticationRequest.getUsername();
        authenticate(username, authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtService.generateToken(userDetails);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @PostMapping("/sign_up")
    public ResponseEntity<Object> users(@ModelAttribute @Valid UserDto.Request.SignUp userDto, Errors errors) {
        var status = HttpStatus.BAD_REQUEST;
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errorsUtil.processErrors(errors), status);
        } else {
            try {
                Long id = signUpService.signUp(userDto);
                status = HttpStatus.OK;
                return new ResponseEntity<>(id, status);
            } catch (DataIntegrityViolationException e) {
                Map<String, String> fieldErrors = new HashMap<>();
                fieldErrors.put("username", "Username already exist.");
                return new ResponseEntity<>(ErrorsDto.builder().fieldErrors(fieldErrors).build(), status);
            }
        }
    }
}
