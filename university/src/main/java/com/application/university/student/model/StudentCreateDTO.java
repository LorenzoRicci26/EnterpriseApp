package com.application.university.student.model;

import com.application.university.student.entity.Grade;
import com.application.university.student.utils.Gender;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record StudentCreateDTO(
        String name,
        String surname,
        Gender gender,
        Integer age,
        String email,
        LocalDate dob,
        String faculty,
        Integer enrollmentYear,
        Boolean active,
        List<Grade> grades
) implements Serializable {
}
