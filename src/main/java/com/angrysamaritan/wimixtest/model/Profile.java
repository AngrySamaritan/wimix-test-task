package com.angrysamaritan.wimixtest.model;

import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
