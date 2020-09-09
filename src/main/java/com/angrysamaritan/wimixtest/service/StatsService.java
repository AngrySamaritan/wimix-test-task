package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.Message;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repos.MessageRepo;
import com.angrysamaritan.wimixtest.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class StatsService {

    private final UserRepo userRepo;
    private final MessageRepo messageRepo;

    public StatsService(UserRepo userRepo, MessageRepo messageRepo) {
        this.userRepo = userRepo;
        this.messageRepo = messageRepo;
    }

    public void sendStats() {

    }

    public long getMessagesAmountByPeriod(User user, Date startDate, Date endDate) {
        List<Message> result = messageRepo.getSentMessagesOfUser(user.getId(), startDate, endDate);
        result.addAll(messageRepo.getReceivedMessagesOfUserByPeriod(user.getId(), startDate, endDate));
        return result.size();
    }

    public long getRegisteredUsersAmountByPeriod(Date startDate, Date endDate) {
        return userRepo.getRegisteredUsersByPeriod(startDate, endDate).size();
    }

}
