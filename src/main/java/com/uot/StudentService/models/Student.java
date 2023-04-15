package com.uot.StudentService.models;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class Student {

    public Student(){}

    public Student(String id, String name, String fatherName, int semester, String department, double cgpa) {
        this.id = id;
        this.name = name;
        this.fatherName = fatherName;
        this.semester = semester;
        this.department = department;
        this.cgpa = cgpa;
    }

    @NotNull(message = "Required ID")
    @NotBlank(message = "is Blank")
//    @Length(min = 6,max = 6,message = "An ID must be 6 characters long")
    @Size(max = 7,min = 7,message = "An ID must be 7 characters long")
    private String id;
    @NotNull(message = "Name field can't be empty")
    @NotBlank(message = "Is Blank")
    @Length(min = 4,max = 24,message = "Enter a valid Name")
    private String name;
    @NotNull(message = "Father Name field can't be empty")
    @NotBlank(message = "is Blank")
    @Length(min = 4,max = 24,message = "Enter a valid Name")
    private String fatherName;
    @NotNull(message = "Required")
    @Min(value = 1,message = "Semester shouldn't be less than 1")
    @Max(value = 8,message = "Semester shouldn't be greater than 8")
    private int semester;
    @NotNull(message = "Required")
    @NotBlank(message = "is Blank")
    private String department;

//    @Digits(integer = 4,fraction = 99,message = "Enter a valid CGPA or GPA")
//    @Length(min = 4,max = 4,message = "CGPA must not Exceed than 4 characters")
    @NotNull(message = "Required")
    private double cgpa;
}
