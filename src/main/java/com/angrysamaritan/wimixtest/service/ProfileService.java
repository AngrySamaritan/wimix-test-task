package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.DTO.UserDto;
import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long createProfile(long id, UserDto.Request.CreateProfile profileDto) throws NotFoundException {
        Profile profile = new Profile();
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User id: " + id + " not found"));
        profile.setEmail(profileDto.getEmail());
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        user.setProfile(profile);
        userRepository.save(user);
        return user.getId();
    }

    public long updateProfile(long id, UserDto.Request.UpdateProfile profileDto) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User id: " + id + " not found"));
        Profile profile = user.getProfile();
        if (profileDto.getEmail() != null && !profileDto.getEmail().equals("")) {
            profile.setEmail(profileDto.getEmail());
        }
        if (profileDto.getFirstName() != null && !profileDto.getFirstName().equals("")) {
            profile.setFirstName(profileDto.getFirstName());
        }
        if (profileDto.getLastName() != null && !profileDto.getLastName().equals("")) {
            profile.setLastName(profileDto.getLastName());
        }
        user.setProfile(profile);
        user = userRepository.save(user);
        return user.getId();
    }
}
