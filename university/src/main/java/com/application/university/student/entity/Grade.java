package com.application.university.student.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Grade implements Serializable {
    private String subject;
    private Integer value;
    private LocalDate examDate;
}