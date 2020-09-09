package com.angrysamaritan.wimixtest.control;

import com.angrysamaritan.wimixtest.service.MailService;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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

    public StatsController(SpringTemplateEngine thymeleafTemplateEngine, MailService mailService) {
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.mailService = mailService;
    }


    @PostMapping("/stats.sendMail")
    public String sendStats(@RequestBody JSONObject request) {
        return null;
    }

    public void sendStatsMessage(String to, String subject, Map<String, Object> templateModel) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("stats.html", thymeleafContext);
        mailService.sendMail(to, htmlBody, subject);
    }
}
