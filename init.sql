create table user
(
    id                bigint auto_increment
        primary key,
    password          varchar(255) null,
    registration_date date         null,
    username          varchar(255) null,
    constraint UK_sb8bbouer5wak8vyiiy4pf2bx
        unique (username)
);

create table profile
(
    id         bigint auto_increment
        primary key,
    email      varchar(255) null,
    first_name varchar(255) null,
    last_name  varchar(255) null,
    user_id    bigint       null,
    constraint FKawh070wpue34wqvytjqr4hj5e
        foreign key (user_id) references user (id)
);


create table message
(
    id           bigint auto_increment
        primary key,
    date         date         null,
    text         varchar(255) null,
    recipient_id bigint       null,
    sender_id    bigint       null,
    constraint FKcnj2qaf5yc36v2f90jw2ipl9b
        foreign key (sender_id) references user (id),
    constraint FKiup8wew331d92o7u3k8d918o3
        foreign key (recipient_id) references user (id)
);

