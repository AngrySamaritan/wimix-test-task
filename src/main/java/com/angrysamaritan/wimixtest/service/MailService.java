package com.angrysamaritan.wimixtest.service;

import javax.mail.MessagingException;

public interface MailService {

    void sendMail(String to, String htmlBody, String subject) throws MessagingException;

}
