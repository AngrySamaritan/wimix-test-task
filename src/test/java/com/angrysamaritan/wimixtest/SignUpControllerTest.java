package com.angrysamaritan.wimixtest;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUserSignUpError() throws Exception {
        JSONObject params = new JSONObject();
        params.put("username", "Andrei");
        params.put("password", "123");
        params.put("password_confirm", "12345");

        mockMvc.perform(post("/user.sign_up")
                .contentType(MediaType.APPLICATION_JSON).content(params.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUserSignUpSuccess() throws Exception {
        JSONObject params = new JSONObject();
        params.put("username", "Andrei");
        params.put("password", "123456");
        params.put("password_confirm", "123456");

        mockMvc.perform(post("/user.sign_up")
                .contentType(MediaType.APPLICATION_JSON).content(params.toString()))
                .andDo(print())
                .andExpect(status().is(200));
    }
}
