CREATE TYPE taskPriority AS ENUM('LOW', 'MEDIUM', 'HIGH');

ALTER TABLE task ALTER COLUMN priority TYPE taskPriority USING priority::taskpriority
