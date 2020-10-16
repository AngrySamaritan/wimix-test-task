package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.DTO.StatsRequest;
import com.angrysamaritan.wimixtest.DTO.UserDto;
import com.angrysamaritan.wimixtest.exceptions.NoEmailSetException;
import com.angrysamaritan.wimixtest.service.implementations.MailServiceImpl;
import com.angrysamaritan.wimixtest.service.implementations.StatsServiceImpl;
import com.angrysamaritan.wimixtest.service.implementations.UserServiceImpl;
import com.angrysamaritan.wimixtest.service.interfaces.StatsService;
import com.angrysamaritan.wimixtest.service.interfaces.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Api
public class StatsController {


    private final MailServiceImpl mailService;
    private final UserService userService;
    private final StatsService statsService;

    public StatsController(MailServiceImpl mailService, UserServiceImpl userService, StatsServiceImpl statsService) {
        this.mailService = mailService;
        this.userService = userService;
        this.statsService = statsService;
    }


    @PostMapping("/stats.sendMail")
    public long sendStats(@RequestBody StatsRequest request) {
        long currentUserId = userService.getCurrentUserId();
        UserDto.Response.Profile currentUserProfile = userService.getProfile(currentUserId);
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
