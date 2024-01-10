package ru.hogwarts.school5.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school5.model.Faculty;
import ru.hogwarts.school5.model.Student;

import java.util.Collection;

@Service
public interface StudentService {
    Student addStudent(Student student);

    Student findStudent(Long id);

    Student editStudent(Long id, Student student);

    void deleteStudent(Long id);

    void getAllStudents();

    Collection<Student> findStudentsByAgeBetween(int min, int max);

    Collection<Student> getStudentsByFaculty(Faculty faculty);

    Student createStudent(Student student);

    Student getById(Long studentId);
}