package com.angrysamaritan.wimixtest.controller;

import com.angrysamaritan.wimixtest.dto.MessageDto;
import com.angrysamaritan.wimixtest.facades.ChatServiceFacade;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatServiceFacade chatServiceFacade;

    public ChatController(ChatServiceFacade chatServiceFacade) {
        this.chatServiceFacade = chatServiceFacade;
    }

    @MessageMapping("/sendMessage")
    public void sendSpecific(@Payload MessageDto msg) {
        chatServiceFacade.saveAndSend(msg);
    }
}
