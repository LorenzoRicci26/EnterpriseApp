package com.application.university.student.service;

import com.application.university.student.event.producer.MessageProducer;
import com.application.university.student.entity.Grade;
import com.application.university.student.entity.Student;
import com.application.university.student.mapper.StudentMapper;
import com.application.university.student.model.StudentCreateDTO;
import com.application.university.student.model.StudentDTO;
import com.application.university.student.model.TopStudentDTO;
import com.application.university.student.repository.StudentRepository;
import com.application.university.student.utils.Gender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private StudentMapper studentMapper;
    @Mock
    private MessageProducer messageProducer;

    private StudentService studentServiceUnderTest;

    @BeforeEach
    void setUp() {
        studentServiceUnderTest =
                new StudentServiceImpl(studentRepository, objectMapper, studentMapper, messageProducer);
    }

    // Tests for addStudent()
    @Test
    void canAddStudent() throws JsonProcessingException {
        // given
        StudentCreateDTO inputDto = new StudentCreateDTO(
                "John",
                "Doe",
                Gender.MALE,
                22,
                "john.doe@example.com",
                LocalDate.of(2001, 5, 15),
                "Computer Science",
                2020,
                true,
                emptyList()
        );

        Student mappedStudent = new Student();
        mappedStudent.setName("John");
        mappedStudent.setSurname("Doe");
        mappedStudent.setGender(Gender.MALE);
        mappedStudent.setAge(22);
        mappedStudent.setEmail("john.doe@example.com");
        mappedStudent.setDob(LocalDate.of(2001, 5, 15));
        mappedStudent.setFaculty("Computer Science");
        mappedStudent.setEnrollmentYear(2020);
        mappedStudent.setActive(true);
        mappedStudent.setGrades(Collections.emptyList());

        Student savedStudent = new Student();
        savedStudent.setId("550e8400-e29b-41d4-a716-446655440000"); // ID generato dal database
        savedStudent.setName("John");
        savedStudent.setSurname("Doe");
        savedStudent.setGender(Gender.MALE);
        savedStudent.setAge(22);
        savedStudent.setEmail("john.doe@example.com");
        savedStudent.setDob(LocalDate.of(2001, 5, 15));
        savedStudent.setFaculty("Computer Science");
        savedStudent.setEnrollmentYear(2020);
        savedStudent.setActive(true);
        savedStudent.setGrades(Collections.emptyList());

        StudentDTO outputDto = new StudentDTO(
                "550e8400-e29b-41d4-a716-446655440000",
                "John",
                "Doe",
                Gender.MALE,
                22,
                "john.doe@example.com",
                LocalDate.of(2001, 5, 15),
                "Computer Science",
                2020,
                true,
                emptyList()
        );

        // when
        when(studentMapper.toStudent(inputDto)).thenReturn(mappedStudent);
        when(studentRepository.findByEmail(mappedStudent.getEmail())).thenReturn(null);
        when(studentRepository.save(mappedStudent)).thenReturn(savedStudent);
        when(objectMapper.writeValueAsString(savedStudent)).thenReturn(String.valueOf(savedStudent));
        when(studentMapper.toStudentDto(savedStudent)).thenReturn(outputDto);

        StudentDTO result = studentServiceUnderTest.addStudent(inputDto);

        // then
        assertThat(result).isEqualTo(outputDto);
        verify(studentMapper).toStudent(inputDto);
        verify(studentRepository).findByEmail(mappedStudent.getEmail());
        verify(studentRepository).save(mappedStudent);
        //verify(objectMapper).writeValueAsString(savedStudent);
        verify(studentMapper).toStudentDto(savedStudent);
    }

    @Test
    void willThrowWhenStudentIsNull() {
        assertThatThrownBy(() -> studentServiceUnderTest.addStudent(null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("studente non valido, riprovare!");
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        StudentCreateDTO studentCreateDTO = new StudentCreateDTO(
                "John",
                "Doe",
                Gender.MALE,
                22,
                "john.doe@example.com",
                LocalDate.of(2001, 5, 15),
                "Computer Science",
                2020,
                true,
                emptyList()
        );

        Student mappedStudent = new Student();
        mappedStudent.setName("John");
        mappedStudent.setSurname("Doe");
        mappedStudent.setGender(Gender.MALE);
        mappedStudent.setAge(22);
        mappedStudent.setEmail("john.doe@example.com");
        mappedStudent.setDob(LocalDate.of(2001, 5, 15));
        mappedStudent.setFaculty("Computer Science");
        mappedStudent.setEnrollmentYear(2020);
        mappedStudent.setActive(true);
        mappedStudent.setGrades(Collections.emptyList());

        // when
        when(studentMapper.toStudent(studentCreateDTO)).thenReturn(mappedStudent);
        when(studentRepository.findByEmail(mappedStudent.getEmail())).thenReturn(mappedStudent);

        // then
        assertThatThrownBy(() -> studentServiceUnderTest.addStudent(studentCreateDTO))
                        .isInstanceOf(ResponseStatusException.class)
                        .hasMessageContaining("Email " + mappedStudent.getEmail() + " taken");
        verify(studentMapper).toStudent(studentCreateDTO);
        verify(studentRepository).findByEmail(mappedStudent.getEmail());
    }

    @Test
    void willThrowWhenJsonProcessFailed() throws JsonProcessingException {
        // given
        StudentCreateDTO inputDto = new StudentCreateDTO(
                "John",
                "Doe",
                Gender.MALE,
                22,
                "john.doe@example.com",
                LocalDate.of(2001, 5, 15),
                "Computer Science",
                2020,
                true,
                emptyList()
        );

        Student mappedStudent = new Student();
        mappedStudent.setName("John");
        mappedStudent.setSurname("Doe");
        mappedStudent.setGender(Gender.MALE);
        mappedStudent.setAge(22);
        mappedStudent.setEmail("john.doe@example.com");
        mappedStudent.setDob(LocalDate.of(2001, 5, 15));
        mappedStudent.setFaculty("Computer Science");
        mappedStudent.setEnrollmentYear(2020);
        mappedStudent.setActive(true);
        mappedStudent.setGrades(Collections.emptyList());

        Student savedStudent = new Student();
        savedStudent.setId("550e8400-e29b-41d4-a716-446655440000"); // ID generato dal database
        savedStudent.setName("John");
        savedStudent.setSurname("Doe");
        savedStudent.setGender(Gender.MALE);
        savedStudent.setAge(22);
        savedStudent.setEmail("john.doe@example.com");
        savedStudent.setDob(LocalDate.of(2001, 5, 15));
        savedStudent.setFaculty("Computer Science");
        savedStudent.setEnrollmentYear(2020);
        savedStudent.setActive(true);
        savedStudent.setGrades(Collections.emptyList());

        // when
        when(studentMapper.toStudent(inputDto)).thenReturn(mappedStudent);
        when(studentRepository.findByEmail(mappedStudent.getEmail())).thenReturn(null);
        when(studentRepository.save(mappedStudent)).thenReturn(savedStudent);
        when(objectMapper.writeValueAsString(any(Student.class)))
                .thenThrow(new JsonProcessingException("Test exception for JSON processing") {});

        // then
        assertThatThrownBy(() -> studentServiceUnderTest.addStudent(inputDto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Error while converting student to json");
        verify(studentMapper).toStudent(inputDto);
        verify(studentRepository).findByEmail(mappedStudent.getEmail());
        verify(studentRepository).save(mappedStudent);
        verify(objectMapper).writeValueAsString(any(Student.class));
    }

    // Tests for getAllStudents()
    @Test
    void canGetAllStudents() {
        // Given
        List<Grade> grades = new ArrayList<>();
        grades.add(new Grade("Science", 25, LocalDate.now()));

        Student student1 = new Student(
                "Mario",
                "Rossi",
                Gender.MALE,
                21,
                "mario.rossi@gmail.com",
                LocalDate.of(1999, 12, 25),
                "ING",
                2021,
                true,
                grades
        );

        Student student2 = new Student(
                "Lucia",
                "Bianchi",
                Gender.FEMALE,
                24,
                "lucia.bianchi@gmail.com",
                LocalDate.of(2000, 11, 20),
                "ING",
                2022,
                true,
                grades
        );

        List<Student> students = Arrays.asList(student1, student2);

        // When
        when(studentRepository.findAll()).thenReturn(students);

        // Then
        assertThat(studentServiceUnderTest.getAllStudents()).isEqualTo(
                students.stream().map(studentMapper::toStudentDto).toList()
        );

        verify(studentRepository).findAll();
    }


    @Test
    void willThrowWhenNoContent() {
        // when
        when(studentRepository.findAll()).thenReturn(emptyList());

        // then
        assertThatThrownBy(() -> studentServiceUnderTest.getAllStudents())
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("No students found.");
        verify(studentRepository).findAll();
    }

    // Tests for InsertVote()
    @Test
    void canInsertVote() {
        // given
        List<Grade> grades = new ArrayList<>();
        grades.add(new Grade("Science",
                25,
                LocalDate.now()));

        Student student = new Student(
                "Mario",
                "Rossi",
                Gender.MALE,
                21,
                "mario.rossi@gmail.com",
                LocalDate.of(1999,12,25),
                "ING",
                2021,
                true,
                grades
        );
        String id = "550e8400-e29b-41d4-a716-446655440000";
        student.setId(id);
        Grade newGrade = new Grade("Math", 29, LocalDate.now());

        // when
        when(studentRepository.findById(String.valueOf(id))).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentServiceUnderTest.insertVote(String.valueOf(id), newGrade);

        // then
        assertNotNull(result);
        assertEquals(2, result.getGrades().size());
        assertEquals(29, result.getGrades().get(1).getValue());
        verify(studentRepository).findById(String.valueOf(id));
        verify(studentRepository).save(student);
    }

    @Test
    void willThrowWhenIdDoesNotExists() {
        // given
        List<Grade> grades = new ArrayList<>();
        grades.add(new Grade("Science",
                25,
                LocalDate.now()));

        Student student = new Student(
                "Mario",
                "Rossi",
                Gender.MALE,
                21,
                "mario.rossi@gmail.com",
                LocalDate.of(1999,12,25),
                "ING",
                2021,
                true,
                grades
        );
        String id = "550e8400-e29b-41d4-a716-446655440000";
        student.setId(id);
        // when
        when(studentRepository.findById(String.valueOf(id))).thenReturn(Optional.of(student));

        // then
        assertThatThrownBy(() -> studentServiceUnderTest.insertVote(String.valueOf(id), null))
                .isInstanceOf(ResponseStatusException.class)
                        .hasMessageContaining("the grade is null, retry.");
        verify(studentRepository).findById(String.valueOf(id));
    }

    @Test
    void willThrowWhenGradeIsNull() {
        // given
        Grade newGrade = new Grade("Math", 29, LocalDate.now());
        // when
        when(studentRepository.findById("123")).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> studentServiceUnderTest.insertVote("123", newGrade))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("student with id " + "123" + " does not exist!");
        verify(studentRepository).findById("123");
    }

    @Test
    void willThrowWhenGradeIsUnder18() {
        // given
        List<Grade> grades = new ArrayList<>();
        grades.add(new Grade("Science",
                25,
                LocalDate.now()));

        Student student = new Student(
                "Mario",
                "Rossi",
                Gender.MALE,
                21,
                "mario.rossi@gmail.com",
                LocalDate.of(1999,12,25),
                "ING",
                2021,
                true,
                grades
        );
        String id = "550e8400-e29b-41d4-a716-446655440000";
        student.setId(id);
        Grade newGrade = new Grade("Math", 17, LocalDate.now());

        // when
        when(studentRepository.findById(String.valueOf(id))).thenReturn(Optional.of(student));

        // then
        assertThatThrownBy(() -> studentServiceUnderTest.insertVote(String.valueOf(id), newGrade))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("the grade must be >= 18");
        verify(studentRepository).findById(String.valueOf(id));
    }

    // Tests for getTopStudents()
    @Test
    void canGetTopStudents() {
        // given
        TopStudentDTO student1 = new TopStudentDTO(
                "1", "Mario", "Rossi", 21, "mario.rossi@example.com",
                LocalDate.of(2002, 5, 10), 29.5, "Engineering", 2021, true
        );
        TopStudentDTO student2 = new TopStudentDTO(
                "2", "Luca", "Bianchi", 22, "luca.bianchi@example.com",
                LocalDate.of(2001, 11, 20), 28.0, "Physics", 2020, true
        );

        List<TopStudentDTO> topStudents = Arrays.asList(student1, student2);

        // when
        when(studentRepository.getTopStudents(28)).thenReturn(topStudents);

        // then
        List<TopStudentDTO> result = studentServiceUnderTest.getTopStudents(28);

        assertThat(result).isEqualTo(topStudents);
        verify(studentRepository).getTopStudents(28);
    }

    @Test
    void willThrowWhenThresholdIsUnder18() {
        assertThatThrownBy(() -> studentServiceUnderTest.getTopStudents(17))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("threshold must be > 18");
    }

    @Test
    void canDeleteStudent() {
        String id = "550e8400-e29b-41d4-a716-446655440000";
        // when
        when(studentRepository.findById(id)).thenReturn(Optional.of(new Student()));

        studentServiceUnderTest.deleteStudent(id);

        verify(studentRepository).findById(id);
        verify(studentRepository).deleteById(id);
    }

    @Test
    void willLogWhenIdIsNotFound() {
        String id = "550e8400-e29b-41d4-a716-446655440000";

        // when
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        studentServiceUnderTest.deleteStudent(id);

        verify(studentRepository).findById(id);
        verify(studentRepository, never()).deleteById(id);
    }
}