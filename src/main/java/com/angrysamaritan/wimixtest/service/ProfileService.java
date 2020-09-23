package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.DTO.UserDto;
import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.utils.UserMapper;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public ProfileService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public long createProfile(long id, UserDto.Request.CreateProfile profileDto) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User id: " + id + " not found"));
        user.setProfile(userMapper.dtoToProfile(profileDto));
        userRepository.save(user);
        return user.getId();
    }

    public long updateProfile(long id, UserDto.Request.UpdateProfile profileDto) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User id: " + id + " not found"));
        Profile profile = user.getProfile();
        user.setProfile(userMapper.dtoToProfile(profileDto, profile));
        user = userRepository.save(user);
        return user.getId();
    }
}
