package com.project.controller;

import com.project.model.Student;
import com.project.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/studenci")
public class StudentRestController {
    private static final Logger logger = LoggerFactory.getLogger(StudentRestController.class);
    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Integer studentId) {
        logger.info("Żądanie REST pobrania studenta: {}", studentId);
        return studentService.getStudent(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudenci() {
        return ResponseEntity.ok(studentService.getStudenci());
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        if (student.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Student result = studentService.setStudent(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        return ResponseEntity.created(location).body(result);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Integer studentId,
            @RequestBody Student student) {
        if (student.getId() == null || !student.getId().equals(studentId)) {
            return ResponseEntity.badRequest().build();
        }
        if (studentService.getStudent(studentId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Student result = studentService.setStudent(student);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}