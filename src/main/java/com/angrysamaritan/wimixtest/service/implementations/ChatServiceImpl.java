package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.dto.MessageDto;
import com.angrysamaritan.wimixtest.exceptions.UserNotFoundException;
import com.angrysamaritan.wimixtest.model.Message;
import com.angrysamaritan.wimixtest.repositories.MessageRepository;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.ChatService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class ChatServiceImpl implements ChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ChatServiceImpl(SimpMessagingTemplate simpMessagingTemplate, MessageRepository messageRepository, UserRepository userRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public void sendMessage(MessageDto msg) {
        LocalDateTime date = LocalDateTime.now();
        String timestamp = String.format("%d.%d %d:%d:%d", date.getDayOfMonth(), date.getMonth().getValue(),
                date.getHour(), date.getMinute(), date.getSecond());
        msg.setTimestamp(timestamp);
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(msg.getRecipientId()),
                "/queue/messages", msg);
    }

    @Transactional
    public void saveMessage(MessageDto msg) {
        Message message = new Message();
        message.setRecipient(userRepository.findById(msg.getSenderId()).orElseThrow(
                ()-> new UserNotFoundException(msg.getSenderId())));
        message.setSender(userRepository.findById(msg.getRecipientId()).orElseThrow(
                ()-> new UserNotFoundException(msg.getRecipientId())));
        message.setText(msg.getText());
        Date date = Date.valueOf(LocalDate.now());
        message.setDate(date);
        messageRepository.save(message);
    }
}
