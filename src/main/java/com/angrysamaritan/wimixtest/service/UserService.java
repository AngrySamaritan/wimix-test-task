package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.ProfileRepository;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> HttpClientErrorException.NotFound.create(HttpStatus.NOT_FOUND, "User id = " + userId + "Not found",
                        HttpHeaders.EMPTY, null, Charset.defaultCharset())
        );
    }

    public JSONObject getProfile(long userId) throws Exception {
        return profilesToJson(Collections.singletonList(getUserById(userId))).getJSONObject(0);
    }

    public JSONArray getProfilesByFirstName(String firstName, int page, int size) throws JSONException {
        Page<User> users = userRepository.getUsersByFirstName(firstName, PageRequest.of(page, size));
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
        return userRepository.getUserByUsername(username);
    }


    public User deleteCurrentProfile() {
        User user = getCurrentUser();
        Profile profile = user.getProfile();
        user.setProfile(null);
        user = userRepository.save(user);
        profileRepository.delete(profile);
        return user;
    }
}
