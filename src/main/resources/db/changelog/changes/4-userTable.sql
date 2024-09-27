CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email    VARCHAR(50),
    password VARCHAR(20) NOT NULL
);

ALTER TABLE task
    ADD user_id INTEGER references users (id);

ALTER TABLE comment
    ADD user_id INTEGER references users (id);

ALTER TABLE users
    ALTER COLUMN password TYPE VARCHAR(100);

ALTER TABLE users
    ADD role VARCHAR(20);

ALTER TABLE users
    ADD is_active BOOLEAN
