package com.angrysamaritan.wimixtest.control;

import com.angrysamaritan.wimixtest.model.PatchUserDto;
import com.angrysamaritan.wimixtest.service.UserService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers(@RequestBody String params) throws Exception {
        try {
            JSONObject paramsJson = new JSONObject(params);
            switch (paramsJson.getString("operation")) {
                case "get_profile":
                    return userService.getProfile(paramsJson.getLong("user_id")).toString();
                case "get_by_first_name":
                    return userService.getProfilesByFirstName(paramsJson.getString("first_name"),
                            paramsJson.getInt("page"), paramsJson.getInt("size")).toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject("{error: wrong operation}").toString();
    }

    @PatchMapping("/users")
    public String patchUsers(@RequestBody @ModelAttribute PatchUserDto userDto) {
        return null;
    }
}
