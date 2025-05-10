package com.application.university.student.mapper;


import com.application.university.student.entity.Student;
import com.application.university.student.model.StudentCreateDTO;
import com.application.university.student.model.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    // From Student to StudentDTO
    public StudentDTO toStudentDto(Student student);

    // From StudentDTO to Student
    public Student toStudent(StudentDTO studentDto);

    @Mapping(target = "id", ignore = true)
    public Student toStudent(StudentCreateDTO studentCreateDTO);
}
