CREATE TABLE task
(
    id       SERIAL PRIMARY KEY,
    type     VARCHAR(20),
    title    VARCHAR(100),
    priority VARCHAR(20),
    deadline DATE,
    version  VARCHAR(20)

)
