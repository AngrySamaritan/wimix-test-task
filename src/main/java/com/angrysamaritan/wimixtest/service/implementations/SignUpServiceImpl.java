package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.dto.SignUpReq;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.SignUpService;
import com.angrysamaritan.wimixtest.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class SignUpServiceImpl implements SignUpService {


    private final UserRepository userRepository;
    private final Mapper mapper;
    private final BCryptPasswordEncoder encoder;


    @Autowired
    public SignUpServiceImpl(UserRepository userRepository, Mapper mapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    @Override
    public long signUp(SignUpReq userDto) {
        User user = mapper.map(userDto, User.class);
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        Date date = Date.valueOf(LocalDate.now());
        user.setRegistrationDate(date);
        user = userRepository.save(user);
        return user.getId();
    }
}
