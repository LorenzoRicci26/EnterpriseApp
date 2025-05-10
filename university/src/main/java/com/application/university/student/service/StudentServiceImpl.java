package com.application.university.student.service;

import com.application.university.student.event.producer.MessageProducer;
import com.application.university.student.entity.Grade;
import com.application.university.student.entity.Student;
import com.application.university.student.mapper.StudentMapper;
import com.application.university.student.model.StudentCreateDTO;
import com.application.university.student.model.StudentDTO;
import com.application.university.student.model.TopStudentDTO;
import com.application.university.student.repository.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ObjectMapper objectMapper;
    private final StudentMapper studentMapper;
    private final MessageProducer messageProducer;

    @Override
    public StudentDTO addStudent(StudentCreateDTO studentCreateDTO) {
        System.out.println("Student arrived: " + studentCreateDTO);
        if(studentCreateDTO == null){
            log.error("student is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "studente non valido, riprovare!");
        }
        System.out.println("No vabbè follia...");
        Student student = studentMapper.toStudent(studentCreateDTO);
        System.out.println("Student to save mapped: " + student);
        boolean existsEmail = studentRepository
                .findByEmail(student.getEmail()) != null;
        if(existsEmail){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email " + student.getEmail() + " taken"
            );
        }
        Student studentSaved = studentRepository.save(student);
        System.out.println("Student saved: " + studentSaved);
//        try {
//            String studentJson = objectMapper.writeValueAsString(studentSaved);
//            messageProducer.sendMessage("student1", studentJson);
//        } catch (JsonProcessingException e) {
//            log.error("Error while converting student to json: {}", e.getMessage());
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while converting student to json");
//        }
        return studentMapper.toStudentDto(studentSaved);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No students found.");
        }
        return students.stream().map(studentMapper::toStudentDto).toList();
    }

    @Override
    public Student insertVote(String id, Grade grade) {

        // Grab the student with the id
        Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalStateException("student with id " + id + " does not exist!"));

        // Checks on the grade
        if(grade == null){
            log.error("grade is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the grade is null, retry.");
        }else if(grade.getValue() < 18){
            log.error("grade must be >= 18");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the grade must be >= 18");
        }

        // Update the student's grades
        student.getGrades().add(grade);

        return studentRepository.save(student);
    }

    @Override
    public List<TopStudentDTO> getTopStudents(double threshold) {

        if(threshold < 18){
            log.error("threshold must be > 18");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "threshold must be > 18");
        }
        return studentRepository.getTopStudents(threshold);
    }

    @Override
    public void deleteStudent(String id) {
        if(studentRepository.findById(id).isEmpty()){
            log.error("student with id {} does not exist!", id);
        }else{
            studentRepository.deleteById(id);
        }
    }
}
