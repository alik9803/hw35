package ru.hogwarts.school5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school5.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByNameIgnoreCase(String name);

    Collection<Faculty> findByColorIgnoreCase(String color);

    Faculty findFacultyById(long id);

}