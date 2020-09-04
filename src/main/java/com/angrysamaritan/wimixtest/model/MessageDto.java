package com.angrysamaritan.wimixtest.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class MessageDto {

    @Getter
    @Setter
    private String timestamp;

    @Getter
    @Setter
    private long senderId;

    @Getter
    @Setter
    private long recipientId;

    @Getter
    @Setter
    private String senderName;

    @Getter
    @Setter
    private String text;

    @Override
    public String toString() {
        return "Message{" +
                "timestamp=" + timestamp +
                ", senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
