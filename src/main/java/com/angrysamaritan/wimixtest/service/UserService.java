package com.angrysamaritan.wimixtest.service;

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
                () -> HttpClientErrorException.NotFound.create(HttpStatus.NOT_FOUND, "Not found",
                        HttpHeaders.EMPTY, null, Charset.defaultCharset())
        );
    }

    public JSONObject getProfile(long userId) throws Exception {
        return usersToJson(Collections.singletonList(getUserById(userId))).getJSONObject(0);
    }

    public JSONArray getProfilesByFirstName(String firstName, int page, int size) throws JSONException {
        Page<User> users = userRepo.getUsersByFirstName(firstName, PageRequest.of(page, size));
        return usersToJson(users);
    }

    private JSONArray usersToJson(Iterable<User> users) throws JSONException {
        JSONArray usersJson = new JSONArray();
        for (User user : users) {
            usersJson.put(
                    new JSONObject()
                            .put("username", user.getUsername())
                            .put("first_name", user.getFirstName())
                            .put("last_name", user.getLastName())
                            .put("email", user.getEmail())
            );
        }
        return usersJson;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.getUserByUsername(username);
    }

    public User patchCurrentUser(JSONObject patchInfo) {
        try {
            User user = getCurrentUser();
            if (patchInfo.has("email")) {
                String email = patchInfo.getString("email");
                //TODO: e-mail validation
                user.setEmail(email);
            }
            if (patchInfo.has("first_name")) {
                String firstName = patchInfo.getString("first_name");
                if (firstName.length() > 0) {
                    user.setEmail(firstName);
                }
            }
            if (patchInfo.has("last_name")) {
                String lastName = patchInfo.getString("last_name");
                if (lastName.length() > 0) {
                    user.setEmail(lastName);
                }
            }
            return user;
        } catch (JSONException e) {
            throw HttpClientErrorException.BadRequest.create(HttpStatus.BAD_REQUEST,
                    "Required param \"user_id\" not found", HttpHeaders.EMPTY, null, Charset.defaultCharset());
        }
    }
}
