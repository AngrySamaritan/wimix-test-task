package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.MailLetter;
import com.angrysamaritan.wimixtest.repositories.MailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.sender}")
    private String sender;

    private final MailRepository mailRepository;

    private final JavaMailSender mailSender;

    public MailServiceImpl(MailRepository mailRepository, @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") JavaMailSender mailSender) {
        this.mailRepository = mailRepository;
        this.mailSender = mailSender;
    }

    public void addToQueue(String to, String htmlBody, String subject) {
        mailRepository.save(MailLetter.builder().subject(subject).text(htmlBody).recipient(to).build());
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void sendMail() throws MessagingException {
        System.out.println("TEST!!!!!!!!!!!!!!!!");
        for (var letter: mailRepository.findAll()) {
            try {
                sendLetter(letter.getRecipient(), letter.getText(), letter.getSubject());
                mailRepository.delete(letter);
            } catch (Exception e) {
                // TODO: log
            }
        }
    }

    private void sendLetter(String to, String htmlBody, String subject) throws MessagingException, MailException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
        msg.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        msg.setContent(htmlBody, "text/html");
        mailSender.send(msg);
    }
}
