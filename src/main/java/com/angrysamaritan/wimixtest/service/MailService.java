package com.angrysamaritan.wimixtest.service;

import org.springframework.mail.MailException;

import javax.mail.MessagingException;
import java.util.Map;

public interface MailService {

    void addToQueue(String to, Map<String, Object> templateModel, String templateName, String subject);

    void sendLetter(String to, Map<String, Object> templateModel, String templateName, String subject)
            throws MessagingException, MailException;
}
