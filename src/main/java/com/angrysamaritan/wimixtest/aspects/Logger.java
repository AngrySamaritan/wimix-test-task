package com.angrysamaritan.wimixtest.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Aspect
@Log4j2
public class Logger {

    @Pointcut(value = "execution(* com.angrysamaritan.wimixtest.service.MailService" +
            ".sendLetter(String, java.util.Map<String, Object>, String, String))" +
            " && args(to, templateModel, templateName, subject)", argNames = "to,templateModel,templateName,subject")
    public void letterSending(String to, Map<String, Object> templateModel, String templateName, String subject) {
    }

    @Around(value = "letterSending(to, templateModel, templateName, subject))",
            argNames = "processedJoinPoint,to,templateModel,templateName,subject")
    public Object interceptLetterSending(ProceedingJoinPoint processedJoinPoint, String to, Map<String, Object> templateModel,
                                         String templateName, String subject) {

        Object result = null;

        try {
            log.info(String.format("Sending mail to %s ...", to));
            result = processedJoinPoint.proceed();
            log.info(String.format("Sent mail to %s successfully", to));
        } catch (Throwable throwable) {
            log.error(String.format("Sending mail to %s failed with %s: %s", to,
                    throwable.getClass().getCanonicalName(), throwable.getMessage()), throwable);
        }

        return result;
    }
}
