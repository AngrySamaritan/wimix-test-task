package com.angrysamaritan.wimixtest.facades;

import com.angrysamaritan.wimixtest.dto.MessageDto;

public interface ChatServiceFacade {

    void saveAndSend(MessageDto messageDto);

}
