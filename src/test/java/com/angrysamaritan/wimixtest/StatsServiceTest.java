package com.angrysamaritan.wimixtest;

import com.angrysamaritan.wimixtest.model.Message;
import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repos.MessageRepo;
import com.angrysamaritan.wimixtest.repos.UserRepo;
import com.angrysamaritan.wimixtest.service.StatsService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StatsServiceTest {

    private User user = new User();

    @Autowired
    UserRepo userRepo;

    List<Message> messages = new LinkedList<>();

    @Autowired
    MessageRepo messageRepo;

    @Before
    public void prepare() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setProfile(new Profile());
        user.getProfile().setEmail("test@mail.test");
        user.setUsername("testUsername");
        user.getProfile().setFirstName("A");
        user.getProfile().setLastName("A");
        user.setPassword(encoder.encode("12345"));
        user = userRepo.save(user);
        Message message = Message.builder().date(Date.valueOf(LocalDate.now().minusDays(1)))
                .recipient(user).sender(null).text("Msg1").build();
        messages.add(messageRepo.save(message));
        message = Message.builder().date(Date.valueOf(LocalDate.now()))
                .recipient(null).sender(user).text("Msg1").build();
        messages.add(messageRepo.save(message));
    }

    @Autowired
    StatsService statsService;

    @Test
    public void testGetMessagesByPeriod() {
        Assert.assertEquals(2, statsService.getMessagesAmountByPeriod(
                user, Date.valueOf(LocalDate.now().minusDays(2)), Date.valueOf(LocalDate.now())));
        Assert.assertEquals(1, statsService.getMessagesAmountByPeriod(
                user, Date.valueOf(LocalDate.now().minusDays(2)), Date.valueOf(LocalDate.now().minusDays(1))));
    }


    @After
    public void deleteAll() {
        for (Message message : messages) {
            messageRepo.delete(message);
        }
        userRepo.delete(user);
    }


}
