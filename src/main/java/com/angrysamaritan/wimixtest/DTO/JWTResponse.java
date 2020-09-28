package com.angrysamaritan.wimixtest.DTO;

import lombok.Getter;

import java.io.Serializable;

public class JWTResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    @Getter
    private final String jwtToken;

    public JWTResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
