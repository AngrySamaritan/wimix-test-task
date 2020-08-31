package com.angrysamaritan.wimixtest.model;

import com.angrysamaritan.wimixtest.validdators.PasswordsMatches;
import com.angrysamaritan.wimixtest.validdators.ValidEmail;
import com.angrysamaritan.wimixtest.validdators.ValidPassword;
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
    private String passwordConfirm;

    @Setter
    @Getter
    @ValidEmail
    private String email;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;
}
