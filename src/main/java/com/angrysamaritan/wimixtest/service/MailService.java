package com.angrysamaritan.wimixtest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MailService {



    @Scheduled(cron = "${mail.info-sending-rate-minutes}")
    public void sendStats() {
        System.out.println(LocalDateTime.now());
    }
}
