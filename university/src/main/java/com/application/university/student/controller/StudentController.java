package com.application.university.student.controller;

import com.application.university.student.entity.Grade;
import com.application.university.student.entity.Student;
import com.application.university.student.model.StudentDTO;
import com.application.university.student.model.TopStudentDTO;
import com.application.university.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/add")
    //@CacheEvict(value = "students", key = "'all-students'")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO studentDto){
        return new ResponseEntity<StudentDTO>(studentService.addStudent(studentDto), HttpStatus.CREATED);
    }

    @GetMapping
    //@Cacheable(value = "students", key = "'all-students'")
    public List<StudentDTO> getAllStudents(){
        log.info("Fetching from database...");
        return studentService.getAllStudents();
    }

    @Transactional
    @PutMapping("/{id}/add/grade")
    public ResponseEntity<Student> insertVote(@PathVariable String id, @RequestBody Grade grade){
        return new ResponseEntity<Student>(studentService.insertVote(id, grade), HttpStatus.CREATED);
    }

    @GetMapping("/top/{threshold}")
    public List<TopStudentDTO> getTopStudents(@PathVariable double threshold){
        return studentService.getTopStudents(threshold);
    }

    @DeleteMapping("/{id}")
    //@CacheEvict(value = "students", key = "'all-students'")
    public void deleteStudents(@PathVariable String id){
        studentService.deleteStudent(id);
    }
}
