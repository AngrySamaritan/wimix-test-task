package com.angrysamaritan.wimixtest;

import com.angrysamaritan.wimixtest.controller.StatsController;
import com.angrysamaritan.wimixtest.model.Message;
import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.MessageRepository;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.JWTService;
import com.angrysamaritan.wimixtest.service.StatsService;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class StatsServiceTest {

    private final List<User> users = new LinkedList<>();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    private final List<Message> messages = new LinkedList<>();

    @Autowired
    MessageRepository messageRepository;

    @Before
    public void prepare() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setProfile(new Profile());
        user.getProfile().setEmail("madcat11072001@gmail.com");
        user.setUsername("testUsername");
        user.getProfile().setFirstName("Andrei");
        user.getProfile().setLastName("A");
        user.setPassword(encoder.encode("12345"));
        users.add(userRepository.save(user));

        user = new User();
        user.setProfile(new Profile());
        user.getProfile().setEmail("test1@mail.test");
        user.setUsername("testUsername1");
        user.getProfile().setFirstName("B");
        user.getProfile().setLastName("B");
        user.setPassword(encoder.encode("12345"));
        users.add(userRepository.save(user));


        user = new User();
        user.setProfile(new Profile());
        user.getProfile().setEmail("test2@mail.test");
        user.setUsername("testUsername2");
        user.getProfile().setFirstName("A");
        user.getProfile().setLastName("A");
        user.setPassword(encoder.encode("12345"));
        users.add(userRepository.save(user));


        user = new User();
        user.setProfile(new Profile());
        user.getProfile().setEmail("test3@mail.test");
        user.setUsername("testUsername3");
        user.getProfile().setFirstName("A");
        user.getProfile().setLastName("A");
        user.setPassword(encoder.encode("12345"));
        users.add(userRepository.save(user));


        Message message = Message.builder().date(Date.valueOf(LocalDate.now().minusDays(1)))
                .recipient(users.get(0)).sender(users.get(2)).text("Msg0").build();
        messages.add(messageRepository.save(message));
        message = Message.builder().date(Date.valueOf(LocalDate.now()))
                .recipient(users.get(2)).sender(users.get(0)).text("Msg2").build();
        messages.add(messageRepository.save(message));
        message = Message.builder().date(Date.valueOf(LocalDate.now()))
                .recipient(users.get(2)).sender(users.get(0)).text("Msg3").build();
        messages.add(messageRepository.save(message));

        generateToken();
    }

    private String token;

    @Autowired
    JWTService jwtService;

    public void generateToken() {
        var user = users.get(0);
        token = jwtService.generateToken(new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), new ArrayList<>()));
    }

    @Autowired
    StatsService statsService;

    @Test
    public void testGetMessagesByPeriod() {
        Assert.assertEquals(3, statsService.getMessagesAmountByPeriod(
                users.get(0), Date.valueOf(LocalDate.now().minusDays(2)), Date.valueOf(LocalDate.now())));
        Assert.assertEquals(1, statsService.getMessagesAmountByPeriod(
                users.get(0), Date.valueOf(LocalDate.now().minusDays(2)), Date.valueOf(LocalDate.now().minusDays(1))));
    }


    @Autowired
    StatsController statsController;

    @Test
    public void statsControllerTest() throws Exception {
        var params = new JSONObject();
        assert userRepository.findById(users.get(0).getId()).orElseThrow().getProfile() != null;
        params.put("start_date", Date.valueOf(LocalDate.now().minusDays(2).toString()));
        params.put("end_date", Date.valueOf(LocalDate.now().toString()));
        MvcResult result = mockMvc.perform(post("/stats.sendMail").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(params.toString())).andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }


    @After
    public void deleteAll() {
        messageRepository.deleteAll(messages);
        userRepository.deleteAll(users);
    }


}
