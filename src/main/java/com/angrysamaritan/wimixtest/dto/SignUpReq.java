package com.angrysamaritan.wimixtest.dto;

import com.angrysamaritan.wimixtest.validators.ValidPassword;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class SignUpReq extends UserDto {

    public SignUpReq(String username, String password, String passwordConfirmation) {
        super(username);
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    @ValidPassword
    private final String password;

    private final String passwordConfirmation;
}
