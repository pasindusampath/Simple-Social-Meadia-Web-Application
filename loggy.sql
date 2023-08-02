create table users
(
    name char(40) not null,
    date date     null,
    constraint users_name_uindex
        unique (name)
);

alter table users
    add primary key (name);

create table logs
(
    uuid            char(40) collate utf8_unicode_ci  not null
        primary key,
    title           char(128) collate utf8_unicode_ci null,
    content         text collate utf8_unicode_ci      null,
    createTimestamp date                              null,
    FileName        varchar(100) charset utf8         null,
    fileType        varchar(45)                       null,
    fileFileData    longblob                          null,
    user_id         char(40)                          null,
    constraint logs_users_name_fk
        foreign key (user_id) references users (name)
            on update cascade on delete cascade
);