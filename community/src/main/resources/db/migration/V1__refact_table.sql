alter table question modify creator bigint not null;
alter table comment modify commentator bigint not null comment '评论人id';


