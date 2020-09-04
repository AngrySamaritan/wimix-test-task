package com.angrysamaritan.wimixtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WimixTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(WimixTestTaskApplication.class, args);
    }

}
