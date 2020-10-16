package com.angrysamaritan.wimixtest.DTO;

import com.angrysamaritan.wimixtest.validators.PasswordsMatches;
import com.angrysamaritan.wimixtest.validators.UniqueUsername;
import com.angrysamaritan.wimixtest.validators.ValidEmail;
import com.angrysamaritan.wimixtest.validators.ValidPassword;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public enum UserDto {
    ;

    private interface Id {
        @Positive long getId();
    }

    private interface Username {
        @NotBlank String getUsername();
    }

    private interface Password {
        @NotBlank String getPassword();
    }

    private interface PasswordConfirmation {
        @NotBlank String getPasswordConfirmation();
    }

    public interface PasswordWithConfirmation extends Password, PasswordConfirmation {

    }

    private interface Profile {
        String getEmail();

        String getFirstName();

        String getLastName();
    }

    public enum Request {
        ;

        @Value
        @PasswordsMatches
        public static class SignUp implements Username, PasswordWithConfirmation {
            String passwordConfirmation;

            @UniqueUsername
            String username;

            @ValidPassword
            String password;
        }


        @Value
        public static class CreateProfile implements UserDto.Profile {

            @NotBlank
            String firstName;

            @NotBlank
            String lastName;

            @ValidEmail
            @NotNull
            String email;
        }

        @Value
        public static class UpdateProfile implements UserDto.Profile {

            String firstName;

            String lastName;

            @ValidEmail
            String email;
        }

        @Value
        public static class Login implements Username, Password {
            String username;

            String password;
        }
    }

    public enum Response {
        ;

        @Value
        @Getter
        public static class Profile implements Id, Username, UserDto.Profile {

            long id;

            String firstName;

            String lastName;

            String email;

            String username;
        }

    }

}
