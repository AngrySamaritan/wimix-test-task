package com.angrysamaritan.wimixtest.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class LogInReq extends UserDto {

    private final String password;

    public LogInReq(String username, String password) {
        super(username);
        this.password = password;
    }
}
