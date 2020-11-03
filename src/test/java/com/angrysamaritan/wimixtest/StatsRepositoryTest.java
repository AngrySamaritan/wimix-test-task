package com.angrysamaritan.wimixtest;

import com.angrysamaritan.wimixtest.repositories.StatsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StatsRepositoryTest {

    @Autowired
    private StatsRepository statsRepository;

    @Test
    @Transactional
    public void getMostCommunicativeTest() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);
//        List<Long> theMostCommunicativeUsersId = userRepository.getTheMostCommunicativeUsers(
//                Date.valueOf(startDate),
//                Date.valueOf(endDate)
//        );
//        List<User> users = userRepository.findAllById(theMostCommunicativeUsersId);
//        System.out.println(theMostCommunicativeUsersId);
        System.out.println(statsRepository.getCommunicativeUserIds(Date.valueOf(startDate),
                Date.valueOf(endDate)));
    }
}
