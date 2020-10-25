package com.angrysamaritan.wimixtest;

import com.angrysamaritan.wimixtest.dto.SignUpReq;
import com.angrysamaritan.wimixtest.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelMapperTest {

    @Autowired
    private ModelMapper mapper;

    @Test
    public void userDtoToModel() {
        SignUpReq userDto = new SignUpReq("andrei", "12345", "12345");
        User actual = mapper.map(userDto, User.class);
        User expected = new User();
        expected.setUsername("andrei");
        expected.setPassword("12345");
        Assert.assertEquals(expected, actual);
    }

}
