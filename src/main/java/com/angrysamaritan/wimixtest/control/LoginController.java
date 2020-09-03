package com.angrysamaritan.wimixtest.control;

import com.angrysamaritan.wimixtest.model.JWTRequest;
import com.angrysamaritan.wimixtest.model.JWTResponse;
import com.angrysamaritan.wimixtest.service.JWTService;
import com.angrysamaritan.wimixtest.service.UserDetailsServiceImpl;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    public LoginController(AuthenticationManager authenticationManager, JWTService jwtService,
                           UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public String login(@RequestBody JWTRequest authenticationRequest) throws JSONException {
        String username = authenticationRequest.getUsername();
        authenticate(username, authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtService.generateToken(userDetails);
        return new JSONObject().put("token", token).toString();
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
