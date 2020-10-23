package com.angrysamaritan.wimixtest.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ProfileDtoResp extends ProfileDto {

    private final UserDto userDto;

    public ProfileDtoResp(String firstName, String lastName, String email, UserDto userDto) {
        super(firstName, lastName, email);
        this.userDto = userDto;
    }
}
