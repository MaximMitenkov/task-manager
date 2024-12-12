create table outbox_messages
(
    id      bigserial primary key,
    payload text        not null,
    topic   varchar(50) not null
)
