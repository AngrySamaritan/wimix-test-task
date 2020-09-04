package com.angrysamaritan.wimixtest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @ManyToOne
    @Getter
    @Setter
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @Getter
    @Setter
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @Getter
    @Setter
    private String text;
}
