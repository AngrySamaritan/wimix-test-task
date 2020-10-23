package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.dto.SignUpReq;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.SignUpService;
import com.angrysamaritan.wimixtest.utils.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements SignUpService {


    private final UserRepository userRepository;

    public SignUpServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long signUp(SignUpReq userDto) {

        return userRepository.save(ModelMapper.map(userDto, User.class)).getId();
    }
}
