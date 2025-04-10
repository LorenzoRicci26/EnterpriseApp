package com.application.university.student.model;

import com.application.university.student.entity.Grade;
import com.application.university.student.utils.Gender;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record StudentDTO(
        String id,
        String name,
        String surname,
        Gender gender,
        Integer age,
        String email,
        LocalDate dob,
        String faculty,
        Integer enrollmentYear,
        Boolean active,
        List<Grade>grades
) implements Serializable {
}
