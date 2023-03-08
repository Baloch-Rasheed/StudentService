package com.uot.StudentService.services;

import com.uot.StudentService.models.Student;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StudentManagementService {
    private List<Student> students = new ArrayList<>();

    public void enrollStudent(Student student){
        students.add(student);
    }
    public List<Student> getStudents(){
        return students;
    }

    public Student getStudent(String id){
        for(Student s:students){
            if(Objects.equals(s.getId(), id)){
                return s;
            }
        }
        return null;
    }

    public boolean deleteStudent(String id){
        if(students.contains(getStudent(id))){
            students.remove(getStudent(id));
            return true;
        }
        return false;
    }
}
