package com.angrysamaritan.wimixtest.DTO;

import lombok.Getter;
import lombok.Setter;

public class ResponseUserDto {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private ProfileDto profileDto;

}
