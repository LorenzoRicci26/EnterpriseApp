package com.application.university.student.model;

import java.time.LocalDate;

public record TopStudentDTO (
        String id,
        String name,
        String surname,
        Integer age,
        String university_email,
        LocalDate date_of_birth,
        Double averageGrade,
        String faculty,
        Integer enrollment_year,
        Boolean isActive
){}
