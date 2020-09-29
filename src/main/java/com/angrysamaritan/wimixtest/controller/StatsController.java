package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.DTO.ErrorsDto;
import com.angrysamaritan.wimixtest.DTO.UserDto;
import com.angrysamaritan.wimixtest.DTO.StatsRequest;
import com.angrysamaritan.wimixtest.service.*;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import java.util.Map;

@Controller
public class StatsController {


    private final SpringTemplateEngine thymeleafTemplateEngine;
    private final MailServiceImpl mailService;
    private final UserService userService;
    private final StatsService statsService;

    public StatsController(SpringTemplateEngine thymeleafTemplateEngine, MailServiceImpl mailService, UserServiceImpl userService, StatsServiceImpl statsService) {
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.mailService = mailService;
        this.userService = userService;
        this.statsService = statsService;
    }


    @PostMapping("/stats.sendMail")
    public ResponseEntity<Object> sendStats(@RequestBody StatsRequest request) throws JSONException, MessagingException {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            UserDto.Response.Profile currentUser = userService.getProfile(userService.getCurrentUserId());
            assert currentUser != null;
            String email = currentUser.getEmail();
            assert email != null;
            Map<String, Object> templateMap = statsService.getStatsMap(request.getStartDate(), request.getEndDate());
            templateMap.put("name", currentUser.getFirstName() != null ?  currentUser.getFirstName() : "User");
            sendStatsMessage(email, "Your report", templateMap);
            status = HttpStatus.OK;
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AssertionError e) {
            var errors = ErrorsDto.builder()
                    .globalError("Sorry, you must set up your profile and email before receiving reports").build();
            return new ResponseEntity<>(errors, status);
        }
    }

    public void sendStatsMessage(String to, String subject, Map<String, Object> templateModel) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("stats.html", thymeleafContext);
        mailService.addToQueue(to, htmlBody, subject);
    }
}
