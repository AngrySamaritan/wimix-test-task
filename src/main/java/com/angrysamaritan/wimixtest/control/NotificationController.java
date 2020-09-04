package com.angrysamaritan.wimixtest.control;

import com.angrysamaritan.wimixtest.service.NotificationService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/echo")
    public String send(String text) {
        notificationService.sendNotification("Test");
        notificationService.sendNotification("Test2");
        return text;
    }
}