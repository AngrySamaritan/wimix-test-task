package com.angrysamaritan.wimixtest.service.interfaces;

import com.angrysamaritan.wimixtest.dto.ProfileCreateReq;
import com.angrysamaritan.wimixtest.dto.ProfileDto;
import com.angrysamaritan.wimixtest.dto.ProfileDtoResp;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;

public interface ProfileService {

    long createProfile(long id, ProfileCreateReq profileCreateReq) throws NotFoundException;

    void updateProfile(long id, ProfileDto profileDto) throws NotFoundException;

    Page<ProfileDto> getProfilesByFirstName(String firstName, int page, int size);

    ProfileDtoResp getProfile(long userId);

    void deleteProfile(long userId);

}
