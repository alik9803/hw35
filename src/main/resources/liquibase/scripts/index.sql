-- liquibase formatted sql

--changeset:1
create index student_name_index on student (name);

--changeset:2
CREATE INDEX idx_faculty_search ON faculty (name, color);