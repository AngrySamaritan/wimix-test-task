package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.dto.*;
import com.angrysamaritan.wimixtest.exceptions.UserNotFoundException;
import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.ProfileRepository;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.ProfileService;
import com.angrysamaritan.wimixtest.utils.ModelMapper;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public long createProfile(long id, ProfileCreateReq profileDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setProfile(ModelMapper.map(profileDto, Profile.class));
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Page<ProfileDto> getProfilesByFirstName(String firstName, int page, int size) {
        Page<Profile> profiles = profileRepository.getUsersByFirstName(firstName, PageRequest.of(page, size));
        List<ProfileDto> dtoList = new LinkedList<>();
        for (Profile profile: profiles) {
            dtoList.add(ModelMapper.map(profile, ProfileDto.class));
        }
        return new PageImpl<>(dtoList);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateProfile(long id, ProfileDto profileDto) {
        Profile profile =  profileRepository.getProfileByUserId(id);
        Profile newProfile = ModelMapper.map(profileDto, Profile.class);
        if (newProfile.getFirstName() != null && newProfile.getFirstName().length() != 0) {
            profile.setFirstName(newProfile.getFirstName());
        }
        if (newProfile.getLastName() != null && newProfile.getLastName().length() != 0) {
            profile.setLastName(newProfile.getLastName());
        }
        if (newProfile.getEmail() != null && newProfile.getEmail().length() != 0) {
            profile.setEmail(newProfile.getEmail());
        }
        profileRepository.save(profile);
    }

    public ProfileDtoResp getProfile(long userId) {
        Profile profile = profileRepository.getProfileByUserId(userId);
        return ModelMapper.map(profile, ProfileDtoResp.class);
    }

    @Override
    public void deleteProfile(long userId) {
        Profile profile = profileRepository.getProfileByUserId(userId);
        User user = profile.getUser();
        user.setProfile(null);
        userRepository.save(user);
        profileRepository.delete(profile);
    }
}
