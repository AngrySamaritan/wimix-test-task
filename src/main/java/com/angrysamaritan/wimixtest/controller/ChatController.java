package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.DTO.MessageDto;
import com.angrysamaritan.wimixtest.service.ChatService;
import com.angrysamaritan.wimixtest.service.UserService;
import javassist.NotFoundException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/sendMessage")
    public void sendSpecific(@Payload MessageDto msg) throws NotFoundException {
        chatService.saveMessage(msg);
        chatService.sendMessage(msg);
    }
}
