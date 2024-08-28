create type priorityType as enum ('LOW', 'MEDIUM', 'HIGH');

alter table task
alter column priority type priorityType using priority::priorityType
