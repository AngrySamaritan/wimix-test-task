package com.angrysamaritan.wimixtest.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate template;

    public NotificationService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendNotification(String text) {
        template.convertAndSend("/topic/notifications", text.getBytes());
    }
}
