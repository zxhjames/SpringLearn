use acc;
create table user
(
    id           int auto_increment  primary key,
    name         varchar(50)  null,
    token        varchar(100) null,
    gmt_create   bigint       null,
    gmt_modified bigint       null,
    accountId    varchar(100) null
);
