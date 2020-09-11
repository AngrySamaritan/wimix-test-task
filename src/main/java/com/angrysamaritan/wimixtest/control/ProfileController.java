package com.angrysamaritan.wimixtest.control;

import com.angrysamaritan.wimixtest.exceptions.BlankProfileFieldException;
import com.angrysamaritan.wimixtest.model.ProfileDto;
import com.angrysamaritan.wimixtest.service.ProfileService;
import com.angrysamaritan.wimixtest.service.UserService;
import com.angrysamaritan.wimixtest.service.ValidationService;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.nio.charset.Charset;
import java.util.Set;

@RestController
public class ProfileController {

    private final UserService userService;
    private final Validator validator;
    private final ValidationService validationService;
    private final ProfileService profileService;

    public ProfileController(UserService userService, Validator validator, ValidationService validationService, ProfileService profileService) {
        this.userService = userService;
        this.validator = validator;
        this.validationService = validationService;
        this.profileService = profileService;
    }

    @GetMapping("/users.getById")
    public String getUsersById(@RequestBody String params) throws Exception {
        try {
            JSONObject paramsJson = new JSONObject(params);
            return userService.getProfile(paramsJson.getLong("user_id")).toString();
        } catch (JSONException e) {
            throw HttpClientErrorException.BadRequest.create(HttpStatus.BAD_REQUEST,
                    "Not all required params found", HttpHeaders.EMPTY, null, Charset.defaultCharset());
        }
    }

    @GetMapping("/users.getByName")
    public String getUsersByName(@RequestBody String params) {
        try {
            JSONObject paramsJson = new JSONObject(params);
            return userService.getProfilesByFirstName(paramsJson.getString("first_name"),
                    paramsJson.getInt("page"), paramsJson.getInt("size")).toString();
        } catch (JSONException e) {
            throw HttpClientErrorException.BadRequest.create(HttpStatus.BAD_REQUEST,
                    "Not all required params found", HttpHeaders.EMPTY, null, Charset.defaultCharset());
        }
    }

    @PatchMapping("/users.patchProfile")
    public ResponseEntity<String> patchUsers(@RequestBody ProfileDto profileDto) throws Exception {
        Set<ConstraintViolation<ProfileDto>> errors = validator.validate(profileDto);
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (errors.size() != 0) {
            response.put("errors", validationService.processErrors(errors));
        } else {
            try {
                response = new JSONObject().put("user_id", profileService.createOrUpdateProfile(
                        userService.getCurrentUser(), profileDto).getId());
                status = HttpStatus.OK;
            } catch (BlankProfileFieldException e) {
                response.put("errors", new JSONObject().put("global", new JSONArray().put(e.getMessage())));
            }
        }
        return new ResponseEntity<>(response.toString(), status);
    }

    @DeleteMapping("/users.deleteProfile")
    public String deleteProfile() throws JSONException {
        return new JSONObject().put("user_id", userService.deleteCurrentProfile().getId()).toString();
    }
}
