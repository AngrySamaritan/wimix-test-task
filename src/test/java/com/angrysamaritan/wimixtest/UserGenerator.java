package com.angrysamaritan.wimixtest;

import com.angrysamaritan.wimixtest.model.Profile;
import com.angrysamaritan.wimixtest.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

public class UserGenerator {

    private static final String[] usernames = {"Username", "SomeName", "TestUsername", "SomeUsername", "PrikolTakoi"};

    private static final String[] firstNames = {"FirstName", "Akakiy", "Vasya", "Abdula"};

    private static final String[] lastNames = {"LastName", "Turkoff", "Ivanov", "SomeFignya"};

    private final static Random random = new Random();

    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User generateUserWithProfile() {
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        Profile profile = Profile.builder().email(firstName + random.nextInt(Integer.MAX_VALUE) + "@mail.ru")
                .firstName(firstName).lastName(lastName).build();
        return generateUser(profile);
    }

    public User generateUserWithoutProfile() {
        return generateUser(null);
    }

    private User generateUser(Profile profile) {
        String username = usernames[random.nextInt(usernames.length)];
        return User.builder().username(username).profile(profile).password(encoder.encode("123456")).build();
    }


}
