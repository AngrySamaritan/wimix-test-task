package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendMessage(Message msg) {
        LocalDateTime date = LocalDateTime.now();
        msg.setTimestamp(String.format("%d.%d %d:%d:%d", date.getDayOfMonth(), date.getMonth().getValue(),
                date.getHour(), date.getMinute(), date.getSecond()));
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(msg.getRecipientId()),
                "/queue/messages", msg);
    }
}
