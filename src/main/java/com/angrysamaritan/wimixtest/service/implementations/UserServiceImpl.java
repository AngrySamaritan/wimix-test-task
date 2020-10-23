package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.ProfileRepository;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.UserService;
import com.angrysamaritan.wimixtest.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProfileRepository profileRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
    }


    @Override
    public long getIdByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        return user.getId();
    }
}
