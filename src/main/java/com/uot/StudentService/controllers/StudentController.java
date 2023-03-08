package com.uot.StudentService.controllers;

import com.uot.StudentService.models.Student;
import com.uot.StudentService.services.AuthService;
import com.uot.StudentService.services.StudentManagementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
    public StudentController(AuthService authService,StudentManagementService service) {
        this.service = service;
        this.authService = authService;
        dummyStudent();
    }
    StudentManagementService service;
    AuthService authService;

    // Student Service
    @GetMapping("/")
    public String students(){
        if(authService.isUserLoggedIn()){
            if(service.getStudents().isEmpty()){
                return "No Student Enrolled";
            }
            return service.getStudents().toString();
        }
        return "You haven't logged in";
    }
    @PostMapping("/student/add")
    public String addStudent(@RequestBody Student student){
        if(authService.isUserLoggedIn()){
            service.enrollStudent(student);
            return "successfully enrolled student";
        }
        return "User isn't logged in!!! please login";
    }
    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable String id){
        if(authService.isUserLoggedIn() && id.length() > 6){
            return service.getStudent(id).toString();
        }
        return "Could not found the user with id:" + id;
    }
    @GetMapping("/student/delete/{id}")
    public String deleteStudent(@PathVariable String id){
        if(authService.isUserLoggedIn() && id.length() > 6){
            service.deleteStudent(id);
            return "You have successfully deleted an Student";
        }
        return "Couldn't deleted the User";
    }

    void dummyStudent(){
        Student s1 = new Student();
        s1.setId("1104260");
        s1.setName("Baloch");
        s1.setDepartment("Computer Science");
        s1.setFatherName("Abdul Rasheed");
        s1.setSemester("Seventh");
        s1.setCgpa("3.83");
        service.enrollStudent(s1);
        Student s2 = new Student();
        s2.setId("1103853");
        s2.setName("Imdad Rind");
        s2.setDepartment("Computer Science");
        s2.setFatherName("Arif Rind");
        s2.setSemester("Seventh");
        s2.setCgpa("3.53");
        service.enrollStudent(s2);
    }
}
