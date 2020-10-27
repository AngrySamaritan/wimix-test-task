package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.model.MailLetter;
import com.angrysamaritan.wimixtest.repositories.MailRepository;
import com.angrysamaritan.wimixtest.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.SerializationHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.sender}")
    private String sender;

    private final MailRepository mailRepository;

    private final SpringTemplateEngine thymeleafTemplateEngine;

    private final JavaMailSender mailSender;

    public MailServiceImpl(MailRepository mailRepository, SpringTemplateEngine thymeleafTemplateEngine, @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") JavaMailSender mailSender) {
        this.mailRepository = mailRepository;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.mailSender = mailSender;
    }

    public void addToQueue(String to, Map<String, Object> templateModel, String templateName, String subject) {
        byte[] templateModelBytes = SerializationHelper.serialize(new HashMap<>(templateModel));
        MailLetter mailLetter = MailLetter.builder().subject(subject).templateName(templateName)
                .recipient(to).templateModel(templateModelBytes).build();
        mailRepository.save(mailLetter);
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void sendMail() {
        for (var letter : mailRepository.findAll()) {
            try {
                Map<String, Object> templateModel = (Map<String, Object>) SerializationHelper.deserialize(
                        letter.getTemplateModel());
                sendLetter(letter.getRecipient(),
                        templateModel,
                        letter.getTemplateName(),
                        letter.getSubject());
                mailRepository.delete(letter);
            } catch (Exception e) {
                log.error(String.format("Error sending mail to mail: %s", letter.getRecipient()));
            }
        }
    }

    private void sendLetter(String to, Map<String, Object> templateModel, String templateName, String subject)
            throws MessagingException, MailException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process(templateName, thymeleafContext);
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
        msg.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        msg.setContent(htmlBody, "text/html");
        mailSender.send(msg);
    }
}
