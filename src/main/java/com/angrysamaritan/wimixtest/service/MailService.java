package com.angrysamaritan.wimixtest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Value("${spring.mail.sender}")
    private String sender;

    private final JavaMailSender mailSender;


    public MailService(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String htmlBody, String subject) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
        msg.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        msg.setContent(htmlBody, "text/html");
        mailSender.send(msg);
    }
}
