package com.angrysamaritan.wimixtest.service;

import com.angrysamaritan.wimixtest.model.Message;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repos.MessageRepo;
import com.angrysamaritan.wimixtest.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {

    private final UserRepo userRepo;
    private final MessageRepo messageRepo;

    public StatsService(UserRepo userRepo, MessageRepo messageRepo) {
        this.userRepo = userRepo;
        this.messageRepo = messageRepo;
    }

    public Map<String, Object> getStatsMap(Date startDate, Date endDate) {
        List<User> theMostCommunicativeUsers = getTheMostCommunicativeUsers(startDate, endDate);
        long registeredAmount = getRegisteredUsersAmountByPeriod(startDate, endDate);
        Map<String, Object> statsMap = new HashMap<>();
        statsMap.put("theMostCommunicativeUsers", theMostCommunicativeUsers);
        statsMap.put("registeredAmount", registeredAmount);
        return statsMap;
    }

    private List<User> getTheMostCommunicativeUsers(Date startDate, Date endDate) {
        List<User> users = userRepo.findAll();
        try {
            if (users.size() == 0) {
                return null;
            }
            List<User> result = new LinkedList<>();
            long maxMessagesAmount = 0;
            for (User user: users) {
                long messagesAmount = getMessagesAmountByPeriod(user, startDate, endDate);
                if (messagesAmount > maxMessagesAmount) {
                    result.clear();
                    result.add(user);
                    maxMessagesAmount = messagesAmount;
                } else if (messagesAmount == maxMessagesAmount) {
                    result.add(user);
                }
            }
            return result;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

    }

    public long getMessagesAmountByPeriod(User user, Date startDate, Date endDate) {
        List<Message> result = messageRepo.getMessagesOfUserByPeriod(user.getId(), startDate, endDate);
        return result.size();
    }

    public long getRegisteredUsersAmountByPeriod(Date startDate, Date endDate) {
        return userRepo.getRegisteredUsersByPeriod(startDate, endDate).size();
    }

}
