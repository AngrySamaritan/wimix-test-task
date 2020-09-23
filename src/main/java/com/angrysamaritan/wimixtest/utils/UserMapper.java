package com.angrysamaritan.wimixtest.utils;

import com.angrysamaritan.wimixtest.DTO.UserDto;
import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    private final BCryptPasswordEncoder encoder;

    public UserMapper(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public UserDto.Response.Profile userToProfileResponseDto(User user) {
        var profile = user.getProfile() != null ? user.getProfile() : new Profile();
        return new UserDto.Response.Profile(user.getId(), profile.getFirstName(), profile.getLastName(),
                profile.getEmail(), user.getUsername());
    }

    public User signUpDtoToUser(UserDto.Request.SignUp userDto) {
        return User.builder().password(encoder.encode(userDto.getPassword())).username(userDto.getUsername())
                .profile(null).build();
    }

    public Profile dtoToProfile(UserDto.Request.UpdateProfile profileDto, Profile profile) {
        if (profileDto.getEmail() != null && !profileDto.getEmail().equals("")) {
            profile.setEmail(profileDto.getEmail());
        }
        if (profileDto.getFirstName() != null && !profileDto.getFirstName().equals("")) {
            profile.setFirstName(profileDto.getFirstName());
        }
        if (profileDto.getLastName() != null && !profileDto.getLastName().equals("")) {
            profile.setLastName(profileDto.getLastName());
        }
        return profile;
    }

    public Profile dtoToProfile(UserDto.Request.CreateProfile profileDto) {
        Profile profile = new Profile();
        profile.setEmail(profileDto.getEmail());
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        return profile;
    }
}
