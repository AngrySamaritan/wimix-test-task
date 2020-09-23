package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.DTO.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface SignUpService {

    long signIn(UserDto.Request.SignUp requestUserDto);

}
