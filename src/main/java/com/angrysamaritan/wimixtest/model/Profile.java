package com.angrysamaritan.wimixtest.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String firstName;

    private String lastName;


    private String email;

    @OneToOne(mappedBy = "profile")
    private User user;
}
