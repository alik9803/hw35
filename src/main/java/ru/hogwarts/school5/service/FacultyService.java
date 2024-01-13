package ru.hogwarts.school5.service;

import ru.hogwarts.school5.model.Faculty;
import ru.hogwarts.school5.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty findFaculty(Long id);

    Faculty editFaculty(Long id, Faculty faculty);

    void deleteFaculty(Long id);

    Collection<Faculty> findFacultiesByNameOrColor(String name, String color);

    Faculty getFacultyByStudent(Student student);

    Object getAllFacultyColor(String anyString);

    Object add(Faculty any);

    Object getFacultyByColor(String color);

    Object getFacultyByNameOrColor(String name, String color);
}