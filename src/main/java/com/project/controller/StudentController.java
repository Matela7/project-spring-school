package com.project.controller;

import com.project.model.Student;
import com.project.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/studenci")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String listStudenci(Model model) {
        model.addAttribute("studenci", studentService.getStudenci());
        return "studentList";
    }

    @GetMapping("/dodaj")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "studentEdit";
    }

    @GetMapping("/edytuj/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        return studentService.getStudent(id)
                .map(student -> {
                    model.addAttribute("student", student);
                    return "studentEdit";
                })
                .orElse("redirect:/studenci");
    }

    @GetMapping("/usun/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return "redirect:/studenci";
    }
    @PostMapping("/zapisz")
    public String saveStudent(@ModelAttribute Student student) {
        studentService.setStudent(student);
        return "redirect:/studenci";
    }
}