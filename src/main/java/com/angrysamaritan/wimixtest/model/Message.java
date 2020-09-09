package com.angrysamaritan.wimixtest.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Getter
    @Setter
    private Date date;

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
