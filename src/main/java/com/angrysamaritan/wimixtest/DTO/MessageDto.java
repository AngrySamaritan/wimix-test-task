package com.angrysamaritan.wimixtest.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
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
