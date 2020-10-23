package com.angrysamaritan.wimixtest.dto;

import com.angrysamaritan.wimixtest.validators.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class UserDto {

    @UniqueUsername
    private final String username;


}
