package com.angrysamaritan.wimixtest.model;

import com.angrysamaritan.wimixtest.validators.PasswordsMatches;
import com.angrysamaritan.wimixtest.validators.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@PasswordsMatches
public class UserDto {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    @ValidPassword
    private String password;

    @Getter
    @Setter
    @JsonProperty("password_confirm")
    private String passwordConfirm;
}
