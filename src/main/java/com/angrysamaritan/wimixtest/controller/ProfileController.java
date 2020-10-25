package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.dto.ProfileCreateReq;
import com.angrysamaritan.wimixtest.dto.ProfileDto;
import com.angrysamaritan.wimixtest.exceptions.ProfileRequestException;
import com.angrysamaritan.wimixtest.service.implementations.ProfileServiceImpl;
import com.angrysamaritan.wimixtest.service.implementations.UserServiceImpl;
import com.angrysamaritan.wimixtest.service.ProfileService;
import com.angrysamaritan.wimixtest.service.UserService;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

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
    public ProfileDto getUsersById(@PathVariable("id") long id) {
        return profileService.getProfile(id);
    }

    @GetMapping(value = "/profiles")
    public Page<ProfileDto> getUsersByName(@RequestParam String firstName, Pageable pageable) {
        return profileService.getProfilesByFirstName(firstName, pageable.getPageNumber(), pageable.getPageSize());
    }

    @PostMapping("/profiles")
    public long createProfile(@ModelAttribute @Valid ProfileCreateReq profileCreateReq,
                              Errors errors, Principal principal) throws NotFoundException {
        if (errors.hasErrors()) {
            throw new ProfileRequestException(errors);
        } else {
            profileService.createProfile(userService.getIdByUsername(principal.getName()), profileCreateReq);
            return 1L;
        }
    }

    @PatchMapping("/profiles")
    public long patchProfile(@ModelAttribute @Valid ProfileDto profileDto,
                             Errors errors, Principal principal) throws NotFoundException {
        if (errors.hasErrors()) {
            throw new ProfileRequestException(errors);
        } else {
            profileService.updateProfile(userService.getIdByUsername(principal.getName()), profileDto);
            return 1L;
        }
    }

    @DeleteMapping("/profiles")
    public long deleteProfile(Principal principal) {
        profileService.deleteProfile(userService.getIdByUsername(principal.getName()));
        return 1L;
    }
}
