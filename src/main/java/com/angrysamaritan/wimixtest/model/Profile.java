package com.angrysamaritan.wimixtest.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile implements Serializable {

    private static final long serialVersionUID = -6792399334594863100L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String firstName;

    private String lastName;


    private String email;

    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "profile", fetch = FetchType.EAGER)
    private User user;
}
