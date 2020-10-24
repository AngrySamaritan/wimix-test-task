package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.dto.MessageDto;

public interface ChatService {

    void sendMessage(MessageDto msg);

    void saveMessage(MessageDto msg);
}
