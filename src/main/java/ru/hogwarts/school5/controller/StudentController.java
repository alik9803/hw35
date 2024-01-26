package ru.hogwarts.school5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school5.model.Faculty;
import ru.hogwarts.school5.model.Student;
import ru.hogwarts.school5.repository.AvatarRepository;
import ru.hogwarts.school5.repository.StudentRepository;
import ru.hogwarts.school5.service.FacultyService;
import ru.hogwarts.school5.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final FacultyService facultyService;
    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students/names")
    public List<String> getStudentNamesStartingWithA() {
        List<String> studentNames = studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());

        return studentNames;
    }

    @GetMapping("/students/average-age-of-students")
    public double getAverageAgeOfStudents() {
        double averageAge = studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);

        return averageAge;
    }


    public StudentController(StudentService studentService, FacultyService facultyService) {
        this.studentService = studentService;
        this.facultyService = facultyService;
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/age-between")
    public ResponseEntity<Collection<Student>> getStudentsByAgeRange(@RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(studentService.findStudentsByAgeBetween(min, max));
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        Faculty faculty = facultyService.getFacultyByStudent(student);
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> editStudent(@RequestBody Student student, @PathVariable Long id) {
        Student foundStudent = studentService.editStudent(id, student);
        if (foundStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/students/count")
    public int getTotalStudentsCount() {
        return studentRepository.getTotalStudentsCount();
    }

    @GetMapping("/students/average-student-age")
    public double getAverageStudentAge() {
        return studentRepository.getAverageStudentAge();
    }

    @GetMapping("/students/last-five")
    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

    @GetMapping("/sum")
    public int getSum() {
        int n = 1_000_000;
        int sum = (n * (n + 1)) / 2;
        return sum;
    }

    @GetMapping("/print-parallel")
    public void printParallel() {
        studentService.printParallel();
    }

    @GetMapping("/print-synchronized")
    public void printSynchronized() {
        studentService.printSynchronized();
    }
}