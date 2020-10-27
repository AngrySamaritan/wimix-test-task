package com.angrysamaritan.wimixtest.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class ProfileDtoResp extends ProfileDto {

    private UserDto user;

}
