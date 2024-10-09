CREATE TABLE users
(
    id        SERIAL PRIMARY KEY,
    username  VARCHAR(50) UNIQUE NOT NULL,
    email     VARCHAR(50),
    password  VARCHAR(100)       NOT NULL,
    role      VARCHAR(20)        NOT NULL,
    is_active BOOLEAN            NOT NULL
);

ALTER TABLE task
    ADD user_id INTEGER references users (id);

ALTER TABLE comment
    ADD user_id INTEGER references users (id);

ALTER TABLE comment
    DROP COLUMN author
