package com.angrysamaritan.wimixtest;

import com.angrysamaritan.wimixtest.model.User;
import com.angrysamaritan.wimixtest.repositories.UserRepository;
import com.angrysamaritan.wimixtest.service.JWTService;
import com.angrysamaritan.wimixtest.service.implementations.UserDetailsServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CreateOrUpdateProfileTest {


    private User user;
    private String token;

    @Before
    public void prepare() {
        createUser();
        generateToken();
    }


    @Autowired
    private UserRepository userRepository;

    private void createUser() {
        user = new User();
        user.setProfile(null);
        user.setUsername("Andrei1");
        user.setPassword(new BCryptPasswordEncoder().encode("12345"));
        user = userRepository.save(user);
    }

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private void generateToken() {
        token = jwtService.generateToken(userDetailsService.loadUserByUsername(user.getUsername()));
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testProfileCreateError() throws Exception {
        var params = new JSONObject();
        params.put("first_name", "Andrei");
        params.put("email", "somemail@a");
        var result = mockMvc.perform(patch("/users.patchProfile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(params.toString()))
                .andExpect(status().isBadRequest())
                .andReturn();
        var response = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertTrue(response.has("errors"));
        Assert.assertTrue(response.getJSONObject("errors").has("email"));
        System.out.println(response);
    }


    @Test
    public void testProfileCreateBlankFields() throws Exception {
        var params = new JSONObject();
        params.put("first_name", "Andrei");
        params.put("email", "somemail@mail.ru");
        var result = mockMvc.perform(patch("/users.patchProfile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(params.toString()))
                .andExpect(status().isBadRequest())
                .andReturn();
        var response = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertTrue(response.has("errors"));
        Assert.assertTrue(response.getJSONObject("errors").has("global"));
        System.out.println(response);
    }

    @Test
    public void testProfileCreateSuccess() throws Exception {
        var params = new JSONObject();
        params.put("first_name", "Andrei");
        params.put("email", "somemail@maila.ru");
        params.put("last_name", "Seeeee");
        var result = mockMvc.perform(patch("/users.patchProfile")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON).content(params.toString()))
                .andExpect(status().isOk())
                .andReturn();
        var response = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertFalse(response.has("errors"));
        System.out.println(response);
    }

    @After
    public void after() {
        userRepository.delete(user);
    }

}
