package com.angrysamaritan.wimixtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    long id;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;


    @Getter
    @Setter
    private String email;

    @OneToOne(mappedBy = "profile")
    @Getter
    @Setter
    private User user;
}
