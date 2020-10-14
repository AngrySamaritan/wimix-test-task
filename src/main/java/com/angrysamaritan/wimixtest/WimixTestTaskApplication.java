package com.angrysamaritan.wimixtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
@Slf4j
@EnableWebMvc
public class WimixTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(WimixTestTaskApplication.class, args);
    }

}
