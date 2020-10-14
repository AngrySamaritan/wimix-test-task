package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.DTO.MessageDto;
import com.angrysamaritan.wimixtest.model.Message;
import com.angrysamaritan.wimixtest.repositories.MessageRepository;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.interfaces.ChatService;
import javassist.NotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
        msg.setTimestamp(String.format("%d.%d %d:%d:%d", date.getDayOfMonth(), date.getMonth().getValue(),
                date.getHour(), date.getMinute(), date.getSecond()));
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(msg.getRecipientId()),
                "/queue/messages", msg);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void saveMessage(MessageDto msg) throws NotFoundException {
        Message message = new Message();
        message.setRecipient(userRepository.findById(msg.getSenderId()).orElseThrow(
                ()-> new NotFoundException("Sender id: " + msg.getSenderId() + " not found")));
        message.setSender(userRepository.findById(msg.getRecipientId()).orElseThrow(
                ()-> new NotFoundException("Recipient id: " + msg.getRecipientId() + " not found")));
        message.setText(msg.getText());
        message.setDate(Date.valueOf(LocalDate.now()));
        messageRepository.save(message);
    }
}