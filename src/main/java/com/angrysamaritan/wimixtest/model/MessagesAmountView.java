package com.angrysamaritan.wimixtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "messages_amount")
@Getter
@Setter
public class MessagesAmountView {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "u_id")
    private long userId;

    @Column(name = "m_count")
    private long messagesCount;

    @Column(name = "m_date")
    private Date messagesDate;

}
