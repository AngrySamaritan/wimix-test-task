package com.angrysamaritan.wimixtest.service.interfaces;

import javax.mail.MessagingException;
import java.util.Map;

public interface MailService {

    void addToQueue(String to, Map<String, Object> templateModel, String templateName, String subject);

    void sendMail() throws MessagingException;
}
