package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.DTO.MessageDto;
import javassist.NotFoundException;

public interface ChatService {

    void sendMessage(MessageDto msg);

    void saveMessage(MessageDto msg) throws NotFoundException;
}
