package com.school.school_management.controller;

import com.school.school_management.dto.SchoolDTO;
import com.school.school_management.entity.Schools;
import com.school.school_management.repository.SchoolsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schools")
@CrossOrigin(origins = "http://localhost:3000")
public class SchoolController {

    @Autowired
    private SchoolsRepository schoolRepository;

    @GetMapping
    public List<SchoolDTO> getAllSchools() {
        List<Schools> schools = schoolRepository.findAll();
        return schools.stream()
                .map(s -> new SchoolDTO(s.getId().longValue(), s.getName()))
                .collect(Collectors.toList());  // ✅ Fix lỗi inference variable
    }
}
