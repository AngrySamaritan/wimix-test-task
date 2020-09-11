package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.exceptions.BlankProfileFieldException;
import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.ProfileDto;
import com.angrysamaritan.wimixtest.model.User;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    public final User createOrUpdateProfile(User user, ProfileDto profileDto) {
        if (user.getProfile() != null) {
            updateProfile(user, profileDto);
        } else {
            createProfile(user, profileDto);
        }
        return user;
    }

    private void createProfile(User user, ProfileDto profileDto) {
        Profile profile = new Profile();
        if (isNullOrEmpty(profileDto.getEmail(), profileDto.getFirstName(), profileDto.getLastName())) {
            throw new BlankProfileFieldException("All request fields must be not blank due profile creating");
        }
        profile.setEmail(profileDto.getEmail());
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        user.setProfile(profile);
    }

    private void updateProfile(User user, ProfileDto profileDto) {
        Profile profile = user.getProfile();
        if (!isNullOrEmpty(profileDto.getEmail())) {
            profile.setEmail(profileDto.getEmail());
        }
        if (!isNullOrEmpty(profileDto.getFirstName())) {
            profile.setFirstName(profileDto.getFirstName());
        }
        if (!isNullOrEmpty(profileDto.getLastName())) {
            profile.setLastName(profileDto.getLastName());
        }
        user.setProfile(profile);
    }

    private boolean isNullOrEmpty(String ... s) {
        for (String v : s) {
            if (v == null || v.length() == 0) {
                return true;
            }
        }
        return false;
    }
}
