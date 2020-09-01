package com.angrysamaritan.wimixtest.model;

import com.angrysamaritan.wimixtest.validators.PasswordsMatches;
import com.angrysamaritan.wimixtest.validators.ValidEmail;
import com.angrysamaritan.wimixtest.validators.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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

    @Setter
    @Getter
    @ValidEmail
    private String email;

    @Getter
    @Setter
    @JsonProperty("first_name")
    @NotBlank
    private String firstName;

    @Getter
    @Setter
    @JsonProperty("last_name")
    @NotBlank
    private String lastName;
}
