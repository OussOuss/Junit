package com.luv2code.springmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.Gradebook;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class OussOussServiceTest {
    @Autowired
    private JdbcTemplate jdbc;
    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private StudentAndGradeService studentAndGradeService;

    @BeforeEach
    public void setupDatabase() {
        jdbc.execute(sqlAddStudent);
        jdbc.execute(sqlAddMathGrade);
        jdbc.execute(sqlAddScienceGrade);
        jdbc.execute(sqlAddHistoryGrade);
    }

    @Test
    public void test_create_user_is_ok() throws Exception {
        //Given

        //When
        studentAndGradeService.createStudent("ouss","oussi","tah.ouss@gmail.com");
        //Then

        assertNotNull(studentDao.findByEmailAddress("tah.ouss@gmail.com"),"Student must not be null !!");
    }

    @Test
    public void test_delete_user_is_ok() throws Exception {
        //Given
        Optional<CollegeStudent> student = studentDao.findById(1);
        assertTrue(student.isPresent(),"Student is present");
        //When
        studentAndGradeService.deleteStudent(1);
        //Then
        student = studentDao.findById(1);
        assertFalse(student.isPresent(),"Student is deleted");
    }

    @Test
    public void test_check_user_is_null_is_ok() throws Exception {
        //Given
        assertTrue(studentAndGradeService.checkIfStudentIsNull(1),"Student is not null");
    }

    @Test
    public void test_student_information_is_ok() throws Exception {
        //Given
        GradebookCollegeStudent gradebookCollegeStudent = studentAndGradeService.studentInformation(1);
        assertNotNull(gradebookCollegeStudent,"Student is not null");
        assertNotNull(gradebookCollegeStudent.getStudentGrades().getMathGradeResults(),"Student Math Grade is not null");
    }

    @Test
    public void test_grade_is_present_is_ok() throws Exception {
        assertTrue(studentAndGradeService.checkIfGradeIsNull(1,"math"),"grade is present");
    }

    @Test
    public void test_grade_is_present_is_ko() throws Exception {
        assertTrue(studentAndGradeService.checkIfGradeIsNull(1,"math"),"grade is arabic");
    }

    @Test
    public void test_delete_grade_is_ok() throws Exception {
        studentAndGradeService.deleteGrade(1,"math");
        GradebookCollegeStudent gradebookCollegeStudent = studentAndGradeService.studentInformation(1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().isEmpty(),"Student Math Grade is empty");
    }

    @Test
    public void test_create_grade_is_ok() throws Exception {
        studentAndGradeService.createStudent("ouss","oussi","tah.ouss@gmail.com");
        CollegeStudent student = studentDao.findByEmailAddress("tah.ouss@gmail.com");

        GradebookCollegeStudent gradebookCollegeStudent = studentAndGradeService.studentInformation(student.getId());

        assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().isEmpty(),"Student Math Grade is empty");
        studentAndGradeService.createGrade(88,student.getId(),"math");

        gradebookCollegeStudent = studentAndGradeService.studentInformation(student.getId());
        assertFalse(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().isEmpty(),"Student Math Grade is empty");
    }

    @Test
    public void test_get_gradebook_is_ok() throws Exception {
        Gradebook gradebook = studentAndGradeService.getGradebook();
        assertNotNull(gradebook,"Gradebook is not null");
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
    }
}
