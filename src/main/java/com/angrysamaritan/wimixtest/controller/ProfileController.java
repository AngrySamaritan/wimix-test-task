package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.DTO.UserDto;
import com.angrysamaritan.wimixtest.service.interfaces.ProfileService;
import com.angrysamaritan.wimixtest.service.implementations.ProfileServiceImpl;
import com.angrysamaritan.wimixtest.service.interfaces.UserService;
import com.angrysamaritan.wimixtest.service.implementations.UserServiceImpl;
import com.angrysamaritan.wimixtest.utils.ErrorsUtil;
import javassist.NotFoundException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProfileController {

    private final UserService userService;
    private final ErrorsUtil errorsUtil;
    private final ProfileService profileService;

    public ProfileController(UserServiceImpl userService, ErrorsUtil errorsUtil, ProfileServiceImpl profileService) {
        this.userService = userService;
        this.errorsUtil = errorsUtil;
        this.profileService = profileService;
    }

    @GetMapping(value = "/profiles", params = "id")
    public UserDto.Response.Profile getUsersById(@RequestParam("id") long id) {
        return userService.getProfile(id);
    }

    @GetMapping(value = "/profiles", params = {"first_name", "page", "size"})
    public List<UserDto.Response.Profile> getUsersByName(@RequestParam("first_name") String firstName,
                                                         @RequestParam("page") int page,
                                                         @RequestParam("size") int size) {
        return userService.getProfilesByFirstName(firstName, page, size);
    }

    @PostMapping("/profiles")
    public ResponseEntity<Object> createProfile(@ModelAttribute @Valid UserDto.Request.CreateProfile profileDto,
                                               Errors errors) throws NotFoundException {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errorsUtil.processErrors(errors), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(profileService.createProfile(
                    userService.getCurrentUserId(), profileDto), HttpStatus.OK);
        }
    }

    @PatchMapping("/profiles")
    public ResponseEntity<Object> patchProfile(@ModelAttribute @Valid UserDto.Request.UpdateProfile profileDto,
                                               Errors errors) throws NotFoundException {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errorsUtil.processErrors(errors), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(profileService.updateProfile(
                    userService.getCurrentUserId(), profileDto), HttpStatus.OK);
        }
    }

    @DeleteMapping("/profiles")
    public String deleteProfile() throws JSONException {
        return new JSONObject().put("user_id", userService.deleteCurrentProfile().getId()).toString();
    }
}
