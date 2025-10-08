package com.school.school_management.dto;

public class SchoolDTO {

    private Long id;
    private String name;

    public SchoolDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
