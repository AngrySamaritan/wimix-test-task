package com.angrysamaritan.wimixtest;

import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void getMostCommunicativeTest() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);
        List<Long> theMostCommunicativeUsersId = userRepository.getTheMostCommunicativeUsers(
                Date.valueOf(startDate),
                Date.valueOf(endDate)
        );
        List<User> users = userRepository.findAllById(theMostCommunicativeUsersId);
        System.out.println(theMostCommunicativeUsersId);
    }
}
