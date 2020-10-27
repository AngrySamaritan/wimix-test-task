package com.angrysamaritan.wimixtest.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MailLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String templateName;


    @Setter
    @Lob
    private byte[] templateModel;


    private String recipient;

    private String subject;
}
