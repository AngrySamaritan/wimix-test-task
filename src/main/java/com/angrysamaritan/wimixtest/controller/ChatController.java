package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.DTO.MessageDto;
import com.angrysamaritan.wimixtest.service.ChatService;
import com.angrysamaritan.wimixtest.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @MessageMapping("/sendMessage")
    public void sendSpecific(@Payload MessageDto msg) {
        chatService.saveMessage(msg.getText(), userService.getUserById(msg.getSenderId()),
                userService.getUserById(msg.getRecipientId()));
        chatService.sendMessage(msg);
    }
}
