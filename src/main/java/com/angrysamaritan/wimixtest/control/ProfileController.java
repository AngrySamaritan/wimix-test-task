package com.angrysamaritan.wimixtest.control;

import com.angrysamaritan.wimixtest.model.ProfileDto;
import com.angrysamaritan.wimixtest.service.UserService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.nio.charset.Charset;

@RestController
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers(@RequestBody String params) throws Exception {
        try {
            JSONObject paramsJson = new JSONObject(params);
            if (paramsJson.has("user_id")) {
                return userService.getProfile(paramsJson.getLong("user_id")).toString();
            }
            if (paramsJson.has("first_name")) {
                return userService.getProfilesByFirstName(paramsJson.getString("first_name"),
                        paramsJson.getInt("page"), paramsJson.getInt("size")).toString();
            }
            throw HttpClientErrorException.BadRequest.create(HttpStatus.BAD_REQUEST,
                    "No operation found matching transmitted params", HttpHeaders.EMPTY, "Bad request".getBytes(), Charset.defaultCharset());
        } catch (JSONException e) {
            throw HttpClientErrorException.BadRequest.create(HttpStatus.BAD_REQUEST,
                    "Not all required params found", HttpHeaders.EMPTY, null, Charset.defaultCharset());
        }
    }

    @PatchMapping("/users")
    public String patchUsers(@RequestBody @Valid ProfileDto profileDto) throws JSONException {
        return new JSONObject().put("user_id", userService.patchCurrentProfile(profileDto).getId()).toString();
    }

    @DeleteMapping("/users")
    public String deleteProfile() throws JSONException {
        return new JSONObject().put("user_id", userService.deleteCurrentProfile().getId()).toString();
    }
}
