--liquibase formatted sql

--changeset authorname:changesetId
create index student_name_index ON student (name);

--changeset authorname:changesetId2
CREATE INDEX idx_faculty_search ON faculty (name, color);