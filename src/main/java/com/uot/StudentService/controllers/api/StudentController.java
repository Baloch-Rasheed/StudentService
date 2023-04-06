package com.uot.StudentService.controllers.api;

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
            service.enrollStudent(student);
            return "successfully enrolled student";
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
        Student s1 = new Student(
                "1104260",
                "Baloch",
                "Abdul Rasheed",
                7,
                "Computer Science",
                3.83
        );
        service.enrollStudent(s1);
        Student s2 = new Student("1103852",
                "Imdad",
                "Arif",
                7,
                "Computer Science",
                3.53);
        service.enrollStudent(s2);
    }
}
