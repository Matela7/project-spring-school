package com.project.service;

import com.project.model.Student;
import com.project.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> getStudent(Integer studentId) {
        logger.info("Pobieranie studenta z ID: {}", studentId);
        return studentRepository.findById(studentId);
    }

    @Override
    public synchronized Student setStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student nie może być null");
        }
        if (student.getId() != null) {
            logger.info("Aktualizacja studenta z ID: {}", student.getId());
        } else {
            logger.info("Tworzenie nowego studenta");
        }
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Integer studentId) {
        logger.info("Usuwanie studenta z ID: {}", studentId);
        studentRepository.deleteById(studentId);
    }

    @Override
    public Page<Student> getStudenci(Pageable pageable) {
        logger.info("Pobieranie stronicowanej listy studentów");
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> searchByNazwisko(String nazwisko, Pageable pageable) {
        logger.info("Wyszukiwanie studentów po nazwisku: {}", nazwisko);
        return studentRepository.findByNazwiskoContainingIgnoreCase(nazwisko, pageable);
    }

    @Override
    public List<Student> getStudenci() {
        logger.info("Pobieranie wszystkich studentów");
        return studentRepository.findAll();
    }
}