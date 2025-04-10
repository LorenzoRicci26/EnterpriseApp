package com.application.university.student.event.consumer;

import com.application.university.student.entity.Student;
import com.application.university.student.repository.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MessageConsumer {

    private final StudentRepository studentRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "student1", groupId = "my-group-id")
    public void listenStudent1(String message) {
        System.out.println("Received message: " + message);
    }

    @KafkaListener(topics = "student2", groupId = "my-group-id")
    public void listenStudent2(String message) {
        try {
            Student student = objectMapper.readValue(message, Student.class);

            if (student.getId() != null && studentRepository.existsById(String.valueOf(student.getId()))) {
                log.info("Student with ID {} already exists, updating", student.getId());
            } else if (studentRepository.findByEmail(student.getEmail()) != null) {
                log.error("Student with email {} already exists, skipping", student.getEmail());
                return;
            }

            Student savedStudent = studentRepository.save(student);
            log.info("Student saved to database with ID: {}", savedStudent.getId());

        } catch (JsonProcessingException e) {
            log.error("Error deserializing student from message: {}", e.getMessage());
        }
    }
}