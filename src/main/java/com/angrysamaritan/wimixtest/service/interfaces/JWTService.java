package com.angrysamaritan.wimixtest.service.interfaces;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JWTService {

    String getUsernameFromToken(String token);

    Date getExpirationDateFromToken(String token);

    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    boolean validateToken(String token, UserDetails userDetails);

}
