package com.school.school_management.controller;

import com.school.school_management.entity.Students;
import com.school.school_management.service.StudentsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {

    private final StudentsService studentService;

    public StudentsController(StudentsService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Students> getAllStudentss() {
        return studentService.getAllStudentss();
    }

    @GetMapping("/{id}")
    public Students getStudentsById(@PathVariable Long id) {
        return studentService.getStudentsById(id);
    }

    @PostMapping
    public Students createStudents(@RequestBody Students student) {
        return studentService.saveStudents(student);
    }

    @PutMapping("/{id}")
    public Students updateStudents(@PathVariable Long id, @RequestBody Students student) {
        return studentService.updateStudents(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudents(@PathVariable Long id) {
        studentService.deleteStudents(id);
    }
}
