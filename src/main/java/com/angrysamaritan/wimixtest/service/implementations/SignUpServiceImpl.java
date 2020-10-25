package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.dto.SignUpReq;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.SignUpService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements SignUpService {


    private final UserRepository userRepository;
    private final ModelMapper mapper;


    public SignUpServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public long signUp(SignUpReq userDto) {
        User map = mapper.map(userDto, User.class);
        User user = userRepository.save(map);
        return user.getId();
    }
}
