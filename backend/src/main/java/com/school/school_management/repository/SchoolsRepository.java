package com.school.school_management.repository;

import com.school.school_management.entity.Schools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolsRepository extends JpaRepository<Schools, Integer> {
}
