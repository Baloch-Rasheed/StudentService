package com.uot.StudentService.controllers;

import com.uot.StudentService.models.Student;
import com.uot.StudentService.services.AuthService;
import com.uot.StudentService.services.StudentManagementService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
public class StudentMVCController {
    public StudentMVCController(AuthService authService, StudentManagementService service) {
        this.service = service;
        this.authService = authService;
    }
    StudentManagementService service;
    AuthService authService;

    // Student Service
    @GetMapping("/")
    public String index(Model model){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        model.addAttribute("time",dateFormat.format(cal.getTime()));
        return "index";
    }
    @GetMapping("/student-list")
    public String students(Model model){
        if(authService.isUserLoggedIn()){
            model.addAttribute("students",service.getStudents());
            return "studentsView";
        }
        return "redirect:/error/auth-error";
    }
    @GetMapping("/student-form")
    public String studentsForm(Model model){
        if(authService.isUserLoggedIn()){
            model.addAttribute("student",new Student());
            return "studentFormView";
        }
        return "redirect:/error/auth-error";
    }
    @PostMapping("/student")
    public String addStudent(@Valid @ModelAttribute Student student, Errors errors){
        if(authService.isUserLoggedIn()){
            if(errors.hasErrors()){
                return "studentFormView";
            }
            service.enrollStudent(student);
            return "redirect:/student-list";
        }
        return "redirect:/error/auth-error";
    }
    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable String id, Model model){
        if(authService.isUserLoggedIn() && id.length() > 6){
            model.addAttribute("student",service.getStudent(id));
            return "studentView";
        }
        return "redirect:/error/auth-error";
    }
    @PostMapping("/student/edit")
    public String updateStudent(@ModelAttribute Student student,Model model){
        if(authService.isUserLoggedIn()){
            if(service.getStudent(student.getId()) != null){
                service.deleteStudent(student.getId());
                model.addAttribute("student",student);
                return "studentFormView";
            }
            return "error-404";
        }
        return "redirect:/error/auth-error";
    }
    @DeleteMapping("/student/{id}")
    public String deleteStudent(@PathVariable String id){
        if(authService.isUserLoggedIn() && id.length() > 6){
            service.deleteStudent(id);
            return "You have successfully deleted an Student";
        }
        return "Couldn't deleted the User";
    }
    @GetMapping("/error/auth-error")
    public String error(){
        return "authErrorView";
    }
    @GetMapping("/error")
    public String err(){
        return "error-404";
    }
}
