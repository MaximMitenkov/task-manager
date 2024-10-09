create table comment
(
    id        serial primary key,
    content   varchar(100) not null,
    author    varchar(30)  not null,
    date_time timestamp    not null,
    task_id   integer      not null references task (id)
);
