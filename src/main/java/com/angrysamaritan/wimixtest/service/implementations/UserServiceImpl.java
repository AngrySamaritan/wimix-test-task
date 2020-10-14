package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.DTO.UserDto;
import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.ProfileRepository;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.interfaces.UserService;
import com.angrysamaritan.wimixtest.utils.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, ProfileRepository profileRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.userMapper = userMapper;
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> HttpClientErrorException.NotFound.create(HttpStatus.NOT_FOUND, "User id = " + userId + "Not found",
                        HttpHeaders.EMPTY, null, Charset.defaultCharset())
        );
    }

    public UserDto.Response.Profile getProfile(long userId) {
        return userMapper.userToProfileResponseDto(getUserById(userId));
    }

    public List<UserDto.Response.Profile> getProfilesByFirstName(String firstName, int page, int size) {
        Page<User> users = userRepository.getUsersByFirstName(firstName, PageRequest.of(page, size));
        List<UserDto.Response.Profile> dtoList = new LinkedList<>();
        for (User user : users) {
            dtoList.add(userMapper.userToProfileResponseDto(user));
        }
        return dtoList;
    }


    public long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.getUserByUsername(username).getId();
    }


    public User deleteCurrentProfile() {
        User user = getUserById(getCurrentUserId());
        Profile profile = user.getProfile();
        user.setProfile(null);
        user = userRepository.save(user);
        profileRepository.delete(profile);
        return user;
    }
}
