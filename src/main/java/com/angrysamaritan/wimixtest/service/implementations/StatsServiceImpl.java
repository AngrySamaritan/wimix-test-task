package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.model.Message;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.MessageRepository;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.StatsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class StatsServiceImpl implements StatsService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public StatsServiceImpl(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Map<String, Object> getStatsMap(Date startDate, Date endDate) {
        List<User> theMostCommunicativeUsers = getTheMostCommunicativeUsers(startDate, endDate);
        long registeredAmount = getRegisteredUsersAmountByPeriod(startDate, endDate);
        Map<String, Object> statsMap = new HashMap<>();
        statsMap.put("theMostCommunicativeUsers", theMostCommunicativeUsers);
        statsMap.put("registeredAmount", registeredAmount);
        return statsMap;
    }

    private List<User> getTheMostCommunicativeUsers(Date startDate, Date endDate) {
        List<User> users = userRepository.findAll();
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

    private long getMessagesAmountByPeriod(User user, Date startDate, Date endDate) {
        List<Message> result = messageRepository.getMessagesOfUserByPeriod(user.getId(), startDate, endDate);
        return result.size();
    }

    private long getRegisteredUsersAmountByPeriod(Date startDate, Date endDate) {
        return userRepository.getRegisteredUsersByPeriod(startDate, endDate).size();
    }

}
