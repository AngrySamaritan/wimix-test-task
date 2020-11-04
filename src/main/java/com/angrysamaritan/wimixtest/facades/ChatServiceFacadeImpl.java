package com.angrysamaritan.wimixtest.facades;

import com.angrysamaritan.wimixtest.dto.MessageDto;
import com.angrysamaritan.wimixtest.service.ChatService;
import com.angrysamaritan.wimixtest.service.implementations.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatServiceFacadeImpl implements ChatServiceFacade{

    private final ChatService chatService;

    @Autowired
    public ChatServiceFacadeImpl(ChatServiceImpl chatService) {
        this.chatService = chatService;
    }

    public void saveAndSend(MessageDto messageDto) {
        chatService.saveMessage(messageDto);
        chatService.sendMessage(messageDto);
    }
}
