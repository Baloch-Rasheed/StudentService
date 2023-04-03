package com.uot.StudentService.models;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class Student {
    @NotNull(message = "Required ID")
//    @NotBlank(message = "is Blank")
    @Length(min = 6,message = "An ID must be 6 characters long")
    private String id;
    @NotNull(message = "Name field can't be empty")
//    @NotBlank(message = "is Blank")
    @Length(min = 4,max = 24,message = "Enter a valid Name")
    private String name;
    @NotNull(message = "Father Name field can't be empty")
//    @NotBlank(message = "is Blank")
    @Length(min = 4,max = 24,message = "Enter a valid Name")
    private String fatherName;
    @NotNull(message = "Required")
//    @NotBlank(message = "is Blank")
    private int semester;
    @NotNull(message = "Required")
//    @NotBlank(message = "is Blank")
    private String department;
    @NotNull(message = "Required")
//    @NotBlank(message = "is Blank")
    @Length(min = 4,max = 4,message = "CGPA must not Exceed than 4 characters")
    @Digits(integer = 4,fraction = 99,message = "Enter a valid CGPA or GPA")
    private double cgpa;
}
