package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repos.UserRepo;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public JSONObject getProfile(long userId) throws Exception {
        userRepo.findById(userId).orElseThrow(Exception::new);
        return null;
    }
    
    public JSONArray getProfilesByFirstName(String firstName, int page, int size) throws JSONException {
        Page<User> users = userRepo.getUsersByFirstName(firstName, PageRequest.of(page, size));
        return usersToJson(users);
    }

    private JSONArray usersToJson(Iterable<User> users) throws JSONException {
        JSONArray usersJson = new JSONArray();
        for(User user: users) {
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
}
