package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.dto.ProfileDtoResp;
import com.angrysamaritan.wimixtest.dto.StatsRequest;
import com.angrysamaritan.wimixtest.exceptions.NoEmailSetException;
import com.angrysamaritan.wimixtest.service.implementations.MailServiceImpl;
import com.angrysamaritan.wimixtest.service.implementations.ProfileServiceImpl;
import com.angrysamaritan.wimixtest.service.implementations.StatsServiceImpl;
import com.angrysamaritan.wimixtest.service.implementations.UserServiceImpl;
import com.angrysamaritan.wimixtest.service.interfaces.ProfileService;
import com.angrysamaritan.wimixtest.service.interfaces.StatsService;
import com.angrysamaritan.wimixtest.service.interfaces.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@Api
public class StatsController {


    private final MailServiceImpl mailService;
    private final UserService userService;
    private final StatsService statsService;
    private final ProfileService profileService;

    @Autowired
    public StatsController(MailServiceImpl mailService, UserServiceImpl userService, StatsServiceImpl statsService,
                           ProfileServiceImpl profileService) {
        this.mailService = mailService;
        this.userService = userService;
        this.statsService = statsService;
        this.profileService = profileService;
    }


    @PostMapping("/sendStatsMail")
    public long sendStats(@RequestBody StatsRequest request, Principal principal) {
        long currentUserId = userService.getIdByUsername(principal.getName());
        ProfileDtoResp currentUserProfile = profileService.getProfile(currentUserId);
        String email;
        if (currentUserProfile == null || (email = currentUserProfile.getEmail()) == null) {
            throw new NoEmailSetException(currentUserId);
        }
        Map<String, Object> templateMap = statsService.getStatsMap(request.getStartDate(), request.getEndDate());
        templateMap.put("name", currentUserProfile.getFirstName() != null ?
                currentUserProfile.getFirstName() : "User");
        sendStatsMessage(email, templateMap, "stats.html", "Your report");
        return 0L;
    }

    public void sendStatsMessage(String to, Map<String, Object> templateModel, String templateName, String subject) {
        mailService.addToQueue(to, templateModel, templateName, subject);
    }
}
