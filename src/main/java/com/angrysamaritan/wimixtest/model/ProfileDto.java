package com.angrysamaritan.wimixtest.model;

import com.angrysamaritan.wimixtest.validators.ValidEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class ProfileDto {
    @Setter
    @Getter
    @ValidEmail
    private String email;

    @Getter
    @Setter
    @JsonProperty("first_name")
    private String firstName;

    @Getter
    @Setter
    @JsonProperty("last_name")
    private String lastName;
}
