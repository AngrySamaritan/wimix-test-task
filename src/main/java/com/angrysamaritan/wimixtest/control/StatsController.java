package com.angrysamaritan.wimixtest.control;

import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.StatsRequest;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.service.MailService;
import com.angrysamaritan.wimixtest.service.StatsService;
import com.angrysamaritan.wimixtest.service.UserService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
    private final MailService mailService;
    private final UserService
            userService;
    private final StatsService statsService;

    public StatsController(SpringTemplateEngine thymeleafTemplateEngine, MailService mailService, UserService userService, StatsService statsService) {
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.mailService = mailService;
        this.userService = userService;
        this.statsService = statsService;
    }


    @PostMapping("/stats.sendMail")
    public ResponseEntity<String> sendStats(@RequestBody StatsRequest request) throws JSONException, MessagingException {

        var result = new JSONObject();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            User currentUser = userService.getCurrentUser();
            Profile profile = currentUser.getProfile();
            assert profile != null;
            String email = profile.getEmail();
            assert email != null;
            Map<String, Object> templateMap = statsService.getStatsMap(request.getStartDate(), request.getEndDate());
            templateMap.put("name", profile.getFirstName());
            sendStatsMessage(email, "Your report", templateMap);
            status = HttpStatus.OK;
        } catch (AssertionError e) {
            result.put("error", new JSONObject()
                    .put("text", "Sorry, you must set up your profile and email before receiving reports"));
        }
        return new ResponseEntity<>(result.toString(), status);
    }

    public void sendStatsMessage(String to, String subject, Map<String, Object> templateModel) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("stats.html", thymeleafContext);
        mailService.sendMail(to, htmlBody, subject);
    }
}
