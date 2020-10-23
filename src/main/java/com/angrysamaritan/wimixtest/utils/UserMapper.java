package com.angrysamaritan.wimixtest.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserMapper(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public static <I, O> O map(I i) {
        return null;
    }

}
