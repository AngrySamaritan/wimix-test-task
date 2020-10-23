package com.angrysamaritan.wimixtest.dto;

import com.angrysamaritan.wimixtest.validators.ValidEmail;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@Value
public class ProfileCreateReq {

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @ValidEmail
    @NotNull
    String email;
}
