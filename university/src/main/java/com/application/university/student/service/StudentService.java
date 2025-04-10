package com.application.university.student.service;

import com.application.university.student.entity.Grade;
import com.application.university.student.entity.Student;
import com.application.university.student.model.StudentDTO;
import com.application.university.student.model.TopStudentDTO;

import java.util.List;

public interface StudentService {

    // Service to add a student
    StudentDTO addStudent(StudentDTO studentDto);

    // Service to get all the students
    List<StudentDTO> getAllStudents();

    // Service to insert a vote for a specific student
    Student insertVote(String id, Grade grade);

    // Service to get all the students with average grades >= threshold
    List<TopStudentDTO> getTopStudents(double threshold);

    // Service to delete a specific student
    void deleteStudent(String id);
}
