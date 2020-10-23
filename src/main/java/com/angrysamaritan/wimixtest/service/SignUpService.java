package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.dto.SignUpReq;
import org.springframework.stereotype.Service;

@Service
public interface SignUpService {

    long signUp(SignUpReq requestUserDto);

}
