package com.angrysamaritan.wimixtest.dto;

import com.angrysamaritan.wimixtest.validators.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class UserDto {

    @UniqueUsername
    private String username;

}
