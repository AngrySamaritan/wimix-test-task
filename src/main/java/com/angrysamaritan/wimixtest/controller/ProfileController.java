package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.DTO.UserDto;
import com.angrysamaritan.wimixtest.exceptions.ProfileRequestException;
import com.angrysamaritan.wimixtest.service.implementations.ProfileServiceImpl;
import com.angrysamaritan.wimixtest.service.implementations.UserServiceImpl;
import com.angrysamaritan.wimixtest.service.interfaces.ProfileService;
import com.angrysamaritan.wimixtest.service.interfaces.UserService;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api
public class ProfileController {

    private final UserService userService;
    private final ProfileService profileService;

    public ProfileController(UserServiceImpl userService, ProfileServiceImpl profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    @GetMapping(value = "/profiles/{id}")
    public UserDto.Response.Profile getUsersById(@PathVariable("id") long id) {
        return userService.getProfile(id);
    }

    @GetMapping(value = "/profiles")
    public List<UserDto.Response.Profile> getUsersByName(@RequestParam("first_name") String firstName,
                                                         @RequestParam("page") int page,
                                                         @RequestParam("size") int size) {
        return userService.getProfilesByFirstName(firstName, page, size);
    }

    @PostMapping("/profiles")
    public long createProfile(@ModelAttribute @Valid UserDto.Request.CreateProfile profileDto,
                              Errors errors) throws NotFoundException {
        if (errors.hasErrors()) {
            throw new ProfileRequestException(errors);
        } else {
            return profileService.createProfile(userService.getCurrentUserId(), profileDto);
        }
    }

    @PatchMapping("/profiles")
    public long patchProfile(@ModelAttribute @Valid UserDto.Request.UpdateProfile profileDto,
                             Errors errors) throws NotFoundException {
        if (errors.hasErrors()) {
            throw new ProfileRequestException(errors);
        } else {
            return profileService.updateProfile(userService.getCurrentUserId(), profileDto);
        }
    }

    @DeleteMapping("/profiles")
    public String deleteProfile() throws JSONException {
        return new JSONObject().put("user_id", userService.deleteCurrentProfile().getId()).toString();
    }
}
