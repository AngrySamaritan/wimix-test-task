package com.angrysamaritan.wimixtest.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class JWTRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    public JWTRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
