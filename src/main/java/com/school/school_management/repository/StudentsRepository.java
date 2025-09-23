package com.school.school_management.repository;

import com.school.school_management.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsRepository extends JpaRepository<Students, Long> {
}
