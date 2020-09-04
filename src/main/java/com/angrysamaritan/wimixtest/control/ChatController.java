package com.angrysamaritan.wimixtest.control;

import com.angrysamaritan.wimixtest.model.Message;
import com.angrysamaritan.wimixtest.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat")
    public void sendSpecific(@Payload Message msg) {
        chatService.sendMessage(msg);
    }
}
