package com.angrysamaritan.wimixtest.control;

import com.angrysamaritan.wimixtest.service.NotificationService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    private final NotificationService notificationService;
    private final SimpMessagingTemplate template;

    public NotificationController(NotificationService notificationService, SimpMessagingTemplate template) {
        this.notificationService = notificationService;
        this.template = template;
    }

    @MessageMapping("/echo")
    public String send(String text) {
        template.convertAndSend("/topic/notifications", "Test");
        template.convertAndSend("/topic/notifications", "Test2");
        return text;
    }
}