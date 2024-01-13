package ru.hogwarts.school5.service;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school5.model.Faculty;
import ru.hogwarts.school5.model.Student;
import ru.hogwarts.school5.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);

    }

    @Override
    public Collection<Faculty> findFacultiesByNameOrColor(String name, String color) {
        if (!name.isEmpty()) {
            return facultyRepository.findByNameIgnoreCase(name);
        } else {
            return facultyRepository.findByColorIgnoreCase(color);
        }
    }

    @Override
    public Faculty getFacultyByStudent(Student student) {
        return student.getFaculty();
    }

    @Override
    public Object getAllFacultyColor(String anyString) {
        return anyString;
    }

    @Override
    public Object add(Faculty any) {
        return add(any);
    }

    @Override
    public Object getFacultyByColor(String color) {
        return getAllFacultyColor(color);
    }

    @Override
    public Object getFacultyByNameOrColor(String name, String color) {
        return getFacultyByNameOrColor(name, color);
    }

    @Override
    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).orElse(new Faculty());
    }

    @Override
    public Faculty editFaculty(Long id, Faculty faculty) {
        return facultyRepository.findById(id)
                .map(foundFaculty -> {
                    foundFaculty.setName(faculty.getName());
                    foundFaculty.setColor(faculty.getColor());
                    return facultyRepository.save(foundFaculty);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id: " + id));
    }

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
}