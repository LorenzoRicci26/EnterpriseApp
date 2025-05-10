package com.application.university.student.entity;

import com.application.university.student.utils.Gender;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Document(collection = "students")
public class Student implements Serializable {
    @Id
    private String id;
    private String name;
    private String surname;
    private Gender gender;
    private Integer age;
    private String email;
    private LocalDate dob;
    private String faculty;
    private Integer enrollmentYear;
    private Boolean active;
    //List of grades
    private List<Grade> grades;

    public Student() {
        this.id = UUID.randomUUID().toString();
    }

    public Student(String name,
                   String surname,
                   Gender gender,
                   Integer age,
                   String email,
                   LocalDate dob,
                   String faculty,
                   Integer enrollmentYear,
                   Boolean active,
                   List<Grade> grades
    ) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.dob = dob;
        this.faculty = faculty;
        this.enrollmentYear = enrollmentYear;
        this.active = active;
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", faculty='" + faculty + '\'' +
                ", enrollmentYear=" + enrollmentYear +
                ", active=" + active +
                ", grades=" + grades +
                '}';
    }
}

