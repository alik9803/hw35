--Шаг 1:
ALTER TABLE Student
    ADD CONSTRAINT age_check CHECK (age >= 16);

ALTER TABLE Student
    ADD CONSTRAINT name_unique_check UNIQUE (name),
    ADD CONSTRAINT name_not_null_check CHECK (name IS NOT NULL);

ALTER TABLE Faculty
    ADD CONSTRAINT name_color_unique_check UNIQUE (name, color);

ALTER TABLE Student
    ALTER COLUMN age SET DEFAULT 20;

--Шаг 2:
CREATE TABLE Person
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    age         INTEGER,
    has_license BOOLEAN
);

CREATE TABLE Car
(
    id    SERIAL PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    cost  DECIMAL(10, 2)
);

CREATE TABLE PersonCar
(
    person_id INTEGER REFERENCES Person (id),
    car_id    INTEGER REFERENCES Car (id),
    PRIMARY KEY (person_id, car_id)
);

--Шаг 3:
SELECT Student.name, Student.age, Faculty.name AS faculty_name
FROM Student
         JOIN Faculty ON Student.id = Faculty.id;

SELECT Student.name, Student.age
FROM Student
         JOIN Avatar ON Student.id = Avatar.id;