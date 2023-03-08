package com.uot.StudentService.models;

import lombok.Data;

@Data
public class Student {
    private String id;
    private String name;
    private String fatherName;
    private String semester;
    private String department;
    private String cgpa;
}
