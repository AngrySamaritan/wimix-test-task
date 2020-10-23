package com.angrysamaritan.wimixtest.service.interfaces;

import com.angrysamaritan.wimixtest.dto.MessageDto;
import javassist.NotFoundException;

public interface ChatService {

    void sendMessage(MessageDto msg);

    void saveMessage(MessageDto msg) throws NotFoundException;
}
