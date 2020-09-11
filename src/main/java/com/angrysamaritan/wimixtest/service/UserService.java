package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.ProfileDto;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repos.ProfileRepo;
import com.angrysamaritan.wimixtest.repos.UserRepo;
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

    private final UserRepo userRepo;
    private final ProfileRepo profileRepo;

    public UserService(UserRepo userRepo, ProfileRepo profileRepo) {
        this.userRepo = userRepo;
        this.profileRepo = profileRepo;
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


    public User deleteCurrentProfile() {
        User user = getCurrentUser();
        Profile profile = user.getProfile();
        user.setProfile(null);
        user = userRepo.save(user);
        profileRepo.delete(profile);
        return user;
    }
}
