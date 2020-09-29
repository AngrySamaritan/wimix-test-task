package com.angrysamaritan.wimixtest.service;

import javax.mail.MessagingException;

public interface MailService {

    void addToQueue(String to, String htmlBody, String subject);

    void sendMail() throws MessagingException;
}
