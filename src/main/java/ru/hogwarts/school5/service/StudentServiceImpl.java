package ru.hogwarts.school5.service;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school5.model.Faculty;
import ru.hogwarts.school5.model.Student;
import ru.hogwarts.school5.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

@Override
    public Collection<Student> findStudentsByAgeBetween(int min, int max) {
        logger.info("Invoked findStudentsByAgeBetween method");
        return studentRepository.findByAgeBetween(min, max);
    }
@Override
    public Collection<Student> getStudentsByFaculty(Faculty faculty) {
        logger.info("Invoked getStudentsByFaculty method");
        return studentRepository.findByFaculty(faculty);
    }
@Override
    public Student createStudent(Student student) {
        logger.info("Invoked createStudent method");
        return studentRepository.save(student);
    }
@Override
    public Student getById(Long studentId) {
        logger.info("Invoked getById method");
        return studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.info("There is not student with id = " + studentId);
                    return new ResourceNotFoundException("Student not found with id: " + studentId);
                });
    }
@Override
    public Student addStudent(Student student) {
        logger.info("Invoked addStudent method");
        return studentRepository.save(student);
    }
@Override
    public Student findStudent(Long id) {
        logger.info("Invoked findStudent method");
        return studentRepository.findById(id).orElse(new Student());
    }
@Override
    public Student editStudent(Long id, Student student) {
        logger.info("Invoked editStudent method");
        return studentRepository.findById(id)
                .map(foundStudent -> {
                    foundStudent.setName(student.getName());
                    foundStudent.setAge(student.getAge());
                    foundStudent.setFaculty(student.getFaculty());
                    return studentRepository.save(foundStudent);
                })
                .orElseThrow(() -> {
                    logger.info("There is not student with id = " + id);
                    return new ResourceNotFoundException("Student not found with id: " + id);
                });
    }
@Override
    public void deleteStudent(Long id) {
        logger.info("Invoked deleteStudent method");
        studentRepository.deleteById(id);
    }
@Override
    public void getAllStudents() {
        logger.info("Invoked getAllStudents method");
        studentRepository.findAll().forEach(System.out::println);
    }
}