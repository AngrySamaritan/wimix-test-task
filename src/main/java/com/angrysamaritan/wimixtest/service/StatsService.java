package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.repos.UserRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final UserRepo userRepo;

    public StatsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Scheduled(cron = "${mail.info-sending-rate-minutes}")
    public void sendStats() {

    }
}
