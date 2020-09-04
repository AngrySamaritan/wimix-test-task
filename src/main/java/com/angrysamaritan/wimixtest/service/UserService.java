package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.ProfileDto;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repos.UserRepo;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.util.Collections;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUserById(long userId) {
        return userRepo.findById(userId).orElseThrow(
                () -> HttpClientErrorException.NotFound.create(HttpStatus.NOT_FOUND, "User id = " + userId + "Not found",
                        HttpHeaders.EMPTY, null, Charset.defaultCharset())
        );
    }

    public JSONObject getProfile(long userId) throws Exception {
        return profilesToJson(Collections.singletonList(getUserById(userId))).getJSONObject(0);
    }

    public JSONArray getProfilesByFirstName(String firstName, int page, int size) throws JSONException {
        Page<User> users = userRepo.getUsersByFirstName(firstName, PageRequest.of(page, size));
        return profilesToJson(users);
    }

    private JSONArray profilesToJson(Iterable<User> users) throws JSONException {
        JSONArray usersJson = new JSONArray();
        for (User user : users) {
            JSONObject userJson = new JSONObject().put("username", user.getUsername());
            JSONObject profile = null;
            if (user.getProfile() != null) {
                        profile = new JSONObject().put("first_name", user.getProfile().getFirstName())
                        .put("last_name", user.getProfile().getLastName())
                        .put("email", user.getProfile().getEmail());
            }
            userJson.put("profile", profile);
            usersJson.put(userJson);
        }
        return usersJson;
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.getUserByUsername(username);
    }

    public User patchCurrentProfile(ProfileDto profileDto) {
        User user = getCurrentUser();
        if (user.getProfile() == null) {
            user.setProfile(new Profile());
        }

        String email = profileDto.getEmail();
        if (email != null) {
            user.getProfile().setEmail(email);
        }
        String firstName = profileDto.getFirstName();
        if (firstName != null) {
            if (firstName.length() > 0) {
                user.getProfile().setFirstName(firstName);
            }
        }
        String lastName = profileDto.getLastName();
        if (lastName != null) {
            if (lastName.length() > 0) {
                user.getProfile().setLastName(lastName);
            }
        }
        return user;
    }
}
