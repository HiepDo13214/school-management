package com.school.school_management.service;

import com.school.school_management.entity.Students;
import com.school.school_management.repository.StudentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentsService {

    private final StudentsRepository studentRepository;

    public StudentsService(StudentsRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Students> getAllStudentss() {
        return studentRepository.findAll();
    }

    public Students getStudentsById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Students saveStudents(Students student) {
        return studentRepository.save(student);
    }

    public Students updateStudents(Long id, Students student) {
        Students existing = studentRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(student.getName());
            existing.setAge(student.getAge());
            return studentRepository.save(existing);
        }
        return null;
    }

    public void deleteStudents(Long id) {
        studentRepository.deleteById(id);
    }
}
