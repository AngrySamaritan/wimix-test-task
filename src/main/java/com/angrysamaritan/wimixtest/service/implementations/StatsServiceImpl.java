package com.angrysamaritan.wimixtest.service.implementations;

import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.StatsRepository;
import com.angrysamaritan.wimixtest.repositories.StatsRepositoryImpl;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsServiceImpl implements StatsService {

    private final UserRepository userRepository;
    private final StatsRepository statsRepository;

    @Autowired
    public StatsServiceImpl(UserRepository userRepository, StatsRepositoryImpl statsRepository) {
        this.userRepository = userRepository;
        this.statsRepository = statsRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getStatsMap(Date startDate, Date endDate) {
        List<User> theMostCommunicativeUsers = getTheMostCommunicativeUsers(startDate, endDate);
        long registeredAmount = getRegisteredUsersAmountByPeriod(startDate, endDate);
        Map<String, Object> statsMap = new HashMap<>();
        statsMap.put("theMostCommunicativeUsers", theMostCommunicativeUsers);
        statsMap.put("registeredAmount", registeredAmount);
        return statsMap;
    }

    private List<User> getTheMostCommunicativeUsers(Date startDate, Date endDate) {
        List<Long> theMostCommunicativeUsersId = statsRepository.getCommunicativeUserIds(startDate, endDate);
        return userRepository.findAllById(theMostCommunicativeUsersId);
    }

    private long getRegisteredUsersAmountByPeriod(Date startDate, Date endDate) {
        List<User> registeredUsers = userRepository.getRegisteredUsersByPeriod(startDate, endDate);
        return registeredUsers.size();
    }

}
