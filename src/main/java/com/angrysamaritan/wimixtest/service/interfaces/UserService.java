package com.angrysamaritan.wimixtest.service.interfaces;

import com.angrysamaritan.wimixtest.DTO.UserDto;
import com.angrysamaritan.wimixtest.model.User;

import java.util.List;

public interface UserService {

    UserDto.Response.Profile getProfile(long userId);

    List<UserDto.Response.Profile> getProfilesByFirstName(String firstName, int page, int size);

    long getCurrentUserId();

    User deleteCurrentProfile();
}
