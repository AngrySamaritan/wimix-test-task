package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.dto.ProfileCreateReq;
import com.angrysamaritan.wimixtest.dto.ProfileDto;
import com.angrysamaritan.wimixtest.dto.ProfileDtoResp;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;

public interface ProfileService {

    void createProfile(long id, ProfileCreateReq profileCreateReq);

    void updateProfile(long id, ProfileDto profileDto);

    Page<ProfileDto> getProfilesByFirstName(String firstName, int page, int size);

    ProfileDtoResp getProfile(long userId);

    void deleteProfile(long userId);

}
