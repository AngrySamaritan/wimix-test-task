package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.DTO.MessageDto;
import com.angrysamaritan.wimixtest.service.interfaces.ChatService;
import com.angrysamaritan.wimixtest.service.implementations.ChatServiceImpl;
import javassist.NotFoundException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatServiceImpl chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/sendMessage")
    public void sendSpecific(@Payload MessageDto msg) throws NotFoundException {
        chatService.saveMessage(msg);
        chatService.sendMessage(msg);
    }
}
