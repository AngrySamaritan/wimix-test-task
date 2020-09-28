package com.angrysamaritan.wimixtest.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "sender")
    @EqualsAndHashCode.Exclude
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient")
    @EqualsAndHashCode.Exclude
    private List<Message> receivedMessages;

    private Date registrationDate;
}
