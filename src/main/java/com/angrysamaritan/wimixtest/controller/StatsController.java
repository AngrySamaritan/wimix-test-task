package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.DTO.ErrorsDto;
import com.angrysamaritan.wimixtest.DTO.StatsRequest;
import com.angrysamaritan.wimixtest.DTO.UserDto;
import com.angrysamaritan.wimixtest.service.implementations.MailServiceImpl;
import com.angrysamaritan.wimixtest.service.implementations.StatsServiceImpl;
import com.angrysamaritan.wimixtest.service.implementations.UserServiceImpl;
import com.angrysamaritan.wimixtest.service.interfaces.StatsService;
import com.angrysamaritan.wimixtest.service.interfaces.UserService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

@RestController
@Api
public class StatsController {


    private final MailServiceImpl mailService;
    private final UserService userService;
    private final StatsService statsService;

    public StatsController(SpringTemplateEngine thymeleafTemplateEngine, MailServiceImpl mailService, UserServiceImpl userService, StatsServiceImpl statsService) {
        this.mailService = mailService;
        this.userService = userService;
        this.statsService = statsService;
    }


    @PostMapping("/stats.sendMail")
    public ResponseEntity<Object> sendStats(@RequestBody StatsRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            UserDto.Response.Profile currentUser = userService.getProfile(userService.getCurrentUserId());
            assert currentUser != null;
            String email = currentUser.getEmail();
            assert email != null;
            Map<String, Object> templateMap = statsService.getStatsMap(request.getStartDate(), request.getEndDate());
            templateMap.put("name", currentUser.getFirstName() != null ? currentUser.getFirstName() : "User");
            sendStatsMessage(email, templateMap, "stats.html", "Your report");
            status = HttpStatus.OK;
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AssertionError e) {
            var errors = ErrorsDto.builder()
                    .globalError("Sorry, you must set up your profile and email before receiving reports").build();
            return new ResponseEntity<>(errors, status);
        }
    }

    public void sendStatsMessage(String to, Map<String, Object> templateModel, String templateName, String subject) {

        mailService.addToQueue(to, templateModel, templateName, subject);
    }
}
