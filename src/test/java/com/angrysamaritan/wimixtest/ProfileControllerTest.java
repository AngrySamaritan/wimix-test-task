package com.angrysamaritan.wimixtest;

import com.angrysamaritan.wimixtest.dto.ProfileDtoResp;
import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.JWTService;
import com.angrysamaritan.wimixtest.service.ProfileService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;

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
        user = userRepository.save(user);
        users.add(user);
        user = new User();
        user.setProfile(new Profile());
        user.getProfile().setEmail("tuser2@masl.test");
        user.setUsername("usr2Username2");
        user.getProfile().setFirstName("A");
        user.getProfile().setLastName("AS");
        user.setPassword(encoder.encode("12345"));
        user = userRepository.save(user);
        users.add(user);
        user = new User();
        user.setProfile(new Profile());
        user.getProfile().setEmail("user3@mail.test");
        user.setUsername("Username3");
        user.getProfile().setFirstName("TRA");
        user.getProfile().setLastName("RA");
        user.setPassword(encoder.encode("12345"));
        user = userRepository.save(user);
        users.add(user);
    }

    @Test
    public void getProfileById() throws Exception {
        User user = users.get(0);

        mockMvc.perform(get("/users").header("Authorization", "Bearer " + token)
                .param("user_id", String.valueOf(user.getId())))
                .andExpect(content().json(profileService.getProfile(user.getId()).toString()));
    }

    @Test
    public void getByNameTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/users.getByName").header("Authorization", "Bearer " + token)
                .param("first_name", "A")
                .param("page", "0")
                .param("size", "2")).andReturn();
        JSONArray users = new JSONArray(result.getResponse().getContentAsString());
        Assert.assertEquals(2, users.length());

        result = mockMvc.perform(get("/users").header("Authorization", "Bearer " + token)
                .param("first_name", "A")
                .param("page", "0")
                .param("size", "1")).andReturn();
        users = new JSONArray(result.getResponse().getContentAsString());
        Assert.assertEquals(1, users.length());
    }

    @Test
    public void deleteProfile() throws Exception {
        mockMvc.perform(delete("/users.deleteProfile")
                .header("Authorization", "Bearer " + token));
        ProfileDtoResp profile = profileService.getProfile(users.get(0).getId());
        Assert.assertNull(profile.getEmail());
    }


    @After
    public void deleteUser() {
        for (User user : users) {
            userRepository.delete(user);
        }
    }

}