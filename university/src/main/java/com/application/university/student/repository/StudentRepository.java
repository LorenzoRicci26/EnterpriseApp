package com.application.university.student.repository;

import com.application.university.student.entity.Student;
import com.application.university.student.model.TopStudentDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    Student findByEmail(String email);

    // renaming successivo alla query
    @Aggregation(pipeline = {
            "{$match: {"
                    + "'grades.value': {$gte: ?0},"
                    + "}}",
            "{$addFields: {"
                    + "averageGrade: {$avg: '$grades.value'}"
                    + "}}",
            "{$match: {averageGrade: {$gt: ?0}}}",
            "{$project: {"
                    + "_id: 0,"
                    + "id: '$_id',"
                    + "name: 1,"
                    + "surname: 1,"
                    + "averageGrade: '$averageGrade',"
                    + "faculty: '$faculty',"
                    + "age: 1,"
                    + "university_email: '$email',"
                    + "date_of_birth: '$dob',"
                    + "enrollment_year: '$enrollmentYear',"
                    + "isActive: '$active'"
                    + "}}",
            "{$sort: {averageGrade: -1}}" // Ordina per media decrescente
    })
    List<TopStudentDTO> getTopStudents(double threshold);

}
