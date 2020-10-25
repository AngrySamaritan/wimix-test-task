package com.angrysamaritan.wimixtest.facades;

import com.angrysamaritan.wimixtest.dto.MessageDto;
import com.angrysamaritan.wimixtest.service.ChatService;
import com.angrysamaritan.wimixtest.service.implementations.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatServiceFacade {

    private final ChatService chatService;

    @Autowired
    public ChatServiceFacade(ChatServiceImpl chatService) {
        this.chatService = chatService;
    }

    public void saveAndSend(MessageDto messageDto) {
        chatService.saveMessage(messageDto);
        chatService.sendMessage(messageDto);
    }
}
