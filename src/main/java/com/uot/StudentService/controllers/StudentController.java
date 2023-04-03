package com.uot.StudentService.controllers;

import com.uot.StudentService.models.Student;
import com.uot.StudentService.services.AuthService;
import com.uot.StudentService.services.StudentManagementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api", produces={"application/json", "text/xml"})
@CrossOrigin(origins = "http://uot.org:8080")
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
    public List<Student> students(){
        if(authService.isUserLoggedIn()){
            return service.getStudents();
        }
        return null;
    }
    @PostMapping("/student")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String addStudent(@RequestBody @Valid Student student, Errors errors){
        if(authService.isUserLoggedIn()){
            if(!errors.hasErrors()){
                service.enrollStudent(student);
                return "successfully enrolled student";
            }
            return errors.getObjectName();
        }
        return "User isn't logged in!!! please login";
    }
    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable String id){
        if(authService.isUserLoggedIn() && id.length() > 6){
            Student st = service.getStudent(id);
            if(st != null){
                return new ResponseEntity<>(st,HttpStatus.OK);
            }
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/student/{id}")
    public String updateStudent(@PathVariable("id") int id, @RequestBody Student student){
        if(authService.isUserLoggedIn()){
            return service.updateStudent(student);
        }
        return "Sorry no User have logged in";
    }

    @DeleteMapping("/student/{id}")
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
        s1.setSemester(7);
        s1.setCgpa(3.83);
        service.enrollStudent(s1);
        Student s2 = new Student();
        s2.setId("1103853");
        s2.setName("Imdad Rind");
        s2.setDepartment("Computer Science");
        s2.setFatherName("Arif Rind");
        s2.setSemester(7);
        s2.setCgpa(3.54);
        service.enrollStudent(s2);
    }
}
