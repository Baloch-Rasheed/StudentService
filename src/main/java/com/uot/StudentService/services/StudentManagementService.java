package com.uot.StudentService.services;

import com.uot.StudentService.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class StudentManagementService {
    @Autowired
    private JdbcTemplate jt;

    public void enrollStudent(Student student){
        jt.update("INSERT INTO student(id,name,fatherName,semester,department,cgpa) values (?,?,?,?,?,?)",
                student.getId(),
                student.getName(),
                student.getFatherName(),
                student.getSemester(),
                student.getDepartment(),
                student.getCgpa()
                );
    }
    public List<Student> getStudents(){
        List<Student> students = jt.query(
                "SELECT * FROM student",
                (rs,rows)-> new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("fatherName"),
                        rs.getInt("semester"),
                        rs.getString("department"),
                        rs.getDouble("cgpa")
                )
        );
        return students;
    }

    public Student getStudent(String id){
        List<Student> students = jt.query(
                "SELECT * FROM student WHERE id=?",
                new Object[]{id},
                (rs,index)-> new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("fatherName"),
                        rs.getInt("semester"),
                        rs.getString("department"),
                        rs.getDouble("cgpa")
                )
        );
        return students.isEmpty()? null : students.get(0);
    }

    public String updateStudent(Student student){
        final Student oldStudent = getStudent(student.getId());
        if(oldStudent != null){
            deleteStudent(student.getId());
            enrollStudent(student);
            return "Successfully updated Student profile";
        }
        return "Student haven't been enrolled";
    }

    public boolean deleteStudent(String id){
        final Student oldStudent = getStudent(id);
        if(oldStudent != null){
            int result = jt.update(
                    "DELETE FROM student WHERE id=?",id
            );
            if(result > -1){
                return true;
            }
        }
        return false;
    }
}
