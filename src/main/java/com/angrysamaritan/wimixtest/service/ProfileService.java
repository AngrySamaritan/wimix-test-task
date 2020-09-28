package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.DTO.UserDto;
import javassist.NotFoundException;

public interface ProfileService {

    long createProfile(long id, UserDto.Request.CreateProfile profileDto) throws NotFoundException;

    long updateProfile(long id, UserDto.Request.UpdateProfile profileDto) throws NotFoundException;
}
