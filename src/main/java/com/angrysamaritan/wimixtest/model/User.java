package com.angrysamaritan.wimixtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    @Getter
    @Setter
    private Profile profile;

    @OneToMany(mappedBy = "sender")
    @Getter
    @Setter
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient")
    @Getter
    @Setter
    private List<Message> receivedMessages;

    @Getter
    @Setter
    private Date registrationDate;
}
