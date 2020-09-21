package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.Message;
import com.angrysamaritan.wimixtest.model.MessageDto;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.MessageRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class ChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;

    public ChatService(SimpMessagingTemplate simpMessagingTemplate, MessageRepository messageRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
    }

    public void sendMessage(MessageDto msg) {
        LocalDateTime date = LocalDateTime.now();
        msg.setTimestamp(String.format("%d.%d %d:%d:%d", date.getDayOfMonth(), date.getMonth().getValue(),
                date.getHour(), date.getMinute(), date.getSecond()));
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(msg.getRecipientId()),
                "/queue/messages", msg);
    }

    public void saveMessage(String text, User sender, User recipient) {
        Message message = new Message();
        message.setRecipient(recipient);
        message.setSender(sender);
        message.setText(text);
        message.setDate(Date.valueOf(LocalDate.now()));
        messageRepository.save(message);
    }
}
