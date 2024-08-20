CREATE table Task
(
    id       bigint primary key,
    type     VARCHAR(20),
    title    VARCHAR(100),
    priority VARCHAR(20),
    deadline DATE
)