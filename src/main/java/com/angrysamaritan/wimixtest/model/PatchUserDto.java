package com.angrysamaritan.wimixtest.model;

import com.angrysamaritan.wimixtest.validators.ValidEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class PatchUserDto {
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
