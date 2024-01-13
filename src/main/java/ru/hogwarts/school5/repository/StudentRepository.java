package ru.hogwarts.school5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school5.model.Faculty;
import ru.hogwarts.school5.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "SELECT COUNT(*) FROM student")
    int getTotalStudentsCount();

    @Query(value = "SELECT AVG(age) FROM student")
    double getAverageStudentAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();

    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findByFaculty(Faculty faculty);

}
