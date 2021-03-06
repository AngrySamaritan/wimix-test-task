package com.angrysamaritan.wimixtest;

import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.JWTService;
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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JWTService jwtService;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private String token;

    @Before
    public void prepare() {
        addUser();
        generateToken();
    }

    public void generateToken() {
        token = jwtService.generateToken(new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), new ArrayList<>()));
    }

    public void addUser() {
        user = new User();
        user.setProfile(new Profile());
        user.getProfile().setEmail("test@mail.test");
        user.setUsername("testUsername");
        user.getProfile().setFirstName("A");
        user.getProfile().setLastName("A");
        user.setPassword(encoder.encode("12345"));
        user = userRepository.save(user);
    }

    @Test
    public void loginUnsuccessful() throws Exception {
        JSONObject params = new JSONObject();
        params.put("username", "notExistingUsername");
        params.put("password", "anyPassword");
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(params.toString()))
                .andExpect(status().is(401));
    }

    @Test
    public void loginSuccessful() throws Exception {

        JSONObject params = new JSONObject();
        params.put("username", "TestUsername");
        params.put("password", "12345");
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(params.toString()))
                .andExpect(status().is(200)).andExpect(
                mvcResult -> Assert.assertNotNull(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void authorizeWithToken() throws Exception {
        mockMvc.perform(post("/").header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @After
    public void deleteUser() {
        userRepository.delete(user);
    }

}
