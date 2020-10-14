package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.DTO.UserDto;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.interfaces.SignUpService;
import com.angrysamaritan.wimixtest.utils.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements SignUpService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public SignUpServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public long signUp(UserDto.Request.SignUp userDto) {
        return userRepository.save(userMapper.signUpDtoToUser(userDto)).getId();
    }
}