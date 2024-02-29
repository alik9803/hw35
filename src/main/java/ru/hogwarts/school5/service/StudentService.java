package ru.hogwarts.school5.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school5.model.Faculty;
import ru.hogwarts.school5.model.Student;

import java.util.Collection;
import java.util.List;

@Service
public interface StudentService {
    Collection<Student> findStudentsByAgeBetween(int min, int max);

    Collection<Student> getStudentsByFaculty(Faculty faculty);

    Student createStudent(Student student);

    Student getById(Long studentId);

    Student addStudent(Student student);

    Student findStudent(Long id);

    Student editStudent(Long id, Student student);

    void deleteStudent(Long id);

    List<Student> getAllStudents();

    void printParallel();

    void printSynchronized();
}
