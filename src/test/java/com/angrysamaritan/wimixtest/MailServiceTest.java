package com.angrysamaritan.wimixtest;

import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.MailService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailServiceTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private MailService mailService;

    private User user;

    @Before
    public void generateUser() {
        var profile = Profile.builder().firstName("Andrei").lastName("Trukhan")
                .email("andrey-truhan@tut.by").build();
        user = User.builder().password(encoder.encode("123456")).profile(profile)
                .username("andrei1107").registrationDate(Date.valueOf(LocalDate.now())).build();
        userRepository.save(user);
    }

    @Test
    public void sendStats() throws InterruptedException {
        mailService.addToQueue(user.getProfile().getEmail(), "<h1>Success</h1>", "Test");
        Thread.sleep(40000);
    }

    @After
    public void delete() {
        userRepository.delete(user);
    }
}
