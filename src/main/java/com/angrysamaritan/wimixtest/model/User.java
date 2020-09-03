package com.angrysamaritan.wimixtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Getter
    @Setter
    @Column(unique = true)
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;


    @Getter
    @Setter
    private String email;
}
