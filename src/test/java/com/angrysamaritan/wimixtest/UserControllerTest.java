package com.angrysamaritan.wimixtest;

import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repos.UserRepo;
import com.angrysamaritan.wimixtest.service.JWTService;
import com.angrysamaritan.wimixtest.service.UserService;
import org.json.JSONArray;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    private final List<User> users = new LinkedList<>();
    private String token;

    @Before
    public void prepare() {
        addUser();
        generateToken();
    }

    public void generateToken() {
        User user = users.get(0);
        token = jwtService.generateToken(new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), new ArrayList<>()));
    }

    public void addUser() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setProfile(new Profile());
        user.getProfile().setEmail("test@mail.test");
        user.setUsername("testUsername");
        user.getProfile().setFirstName("A");
        user.getProfile().setLastName("A");
        user.setPassword(encoder.encode("12345"));
        user = userRepo.save(user);
        users.add(user);
        user = new User();
        user.setProfile(new Profile());
        user.getProfile().setEmail("tuser2@masl.test");
        user.setUsername("usr2Username2");
        user.getProfile().setFirstName("A");
        user.getProfile().setLastName("AS");
        user.setPassword(encoder.encode("12345"));
        user = userRepo.save(user);
        users.add(user);
        user = new User();
        user.setProfile(new Profile());
        user.getProfile().setEmail("user3@mail.test");
        user.setUsername("Username3");
        user.getProfile().setFirstName("TRA");
        user.getProfile().setLastName("RA");
        user.setPassword(encoder.encode("12345"));
        user = userRepo.save(user);
        users.add(user);
    }

    @Test
    public void getProfileById() throws Exception {
        User user = users.get(0);
        JSONObject params = new JSONObject();
        params.put("user_id", user.getId());
        mockMvc.perform(get("/users").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(params.toString()))
                .andExpect(content().json(userService.getProfile(user.getId()).toString()));
    }

    @Test
    public void getByNameTest() throws Exception {
        JSONObject params = new JSONObject();
        params.put("first_name", "A");
        params.put("page", "0");
        params.put("size", "2");
        MvcResult result = mockMvc.perform(get("/users").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(params.toString())).andReturn();
        JSONArray users = new JSONArray(result.getResponse().getContentAsString());
        Assert.assertEquals(2, users.length());

        params.put("size", "1");
        result = mockMvc.perform(get("/users").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(params.toString())).andReturn();
        users = new JSONArray(result.getResponse().getContentAsString());
        Assert.assertEquals(1, users.length());
    }

    @After
    public void deleteUser() {
        for (User user : users) {
            userRepo.delete(user);
        }
    }

}