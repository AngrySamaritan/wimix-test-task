package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.dto.ProfileCreateReq;
import com.angrysamaritan.wimixtest.dto.ProfileDto;
import com.angrysamaritan.wimixtest.dto.ProfileDtoResp;
import com.angrysamaritan.wimixtest.dto.UserDto;
import com.angrysamaritan.wimixtest.exceptions.NotFoundException;
import com.angrysamaritan.wimixtest.exceptions.ProfileNotFoundException;
import com.angrysamaritan.wimixtest.exceptions.UserNotFoundException;
import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.ProfileRepository;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.ProfileService;
import com.angrysamaritan.wimixtest.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final Mapper mapper;

    @Autowired
    public ProfileServiceImpl(UserRepository userRepository, ProfileRepository profileRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void createProfile(long id, ProfileCreateReq profileDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setProfile(mapper.map(profileDto, Profile.class));
        userRepository.save(user);
    }

    @Override
    public Page<ProfileDto> getProfilesByFirstName(String firstName, int page, int size) {
        Page<Profile> profiles = profileRepository.getUsersByFirstName(firstName, PageRequest.of(page, size));
        List<ProfileDto> dtoList = new LinkedList<>();
        for (Profile profile: profiles) {
            ProfileDtoResp profileDto = mapper.map(profile, ProfileDtoResp.class);
            profileDto.setUser(mapper.map(profile.getUser(), UserDto.class));
            dtoList.add(profileDto);
        }
        return new PageImpl<>(dtoList);
    }

    @Transactional
    public void updateProfile(long id, ProfileDto profileDto) {
        Profile profile =  profileRepository.getProfileByUserId(id);
        Profile newProfile = mapper.map(profileDto, Profile.class);
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

    public ProfileDtoResp getProfile(long userId) throws NotFoundException {
        Profile profile = profileRepository.getProfileByUserId(userId);
        if (profile == null) {
            throw new ProfileNotFoundException(userId);
        }
        ProfileDtoResp profileDtoResp = mapper.map(profile, ProfileDtoResp.class);
        profileDtoResp.setUser(mapper.map(profile.getUser(), UserDto.class));
        return profileDtoResp;
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
