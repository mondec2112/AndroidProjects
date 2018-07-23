package com.example.monsanity.edusoft.Container;

/**
 * Created by monsanity on 4/25/18.
 */

public class StudentGrade {
    String student_id;
    int prog_grade;
    int test_grade;
    int exam_grade;
    int final_grade;
    String rank;

    public StudentGrade() {
    }

    public StudentGrade(String student_id, int prog_grade, int test_grade, int exam_grade, int final_grade, String rank) {
        this.student_id = student_id;
        this.prog_grade = prog_grade;
        this.test_grade = test_grade;
        this.exam_grade = exam_grade;
        this.final_grade = final_grade;
        this.rank = rank;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public int getProg_grade() {
        return prog_grade;
    }

    public void setProg_grade(int prog_grade) {
        this.prog_grade = prog_grade;
    }

    public int getTest_grade() {
        return test_grade;
    }

    public void setTest_grade(int test_grade) {
        this.test_grade = test_grade;
    }

    public int getExam_grade() {
        return exam_grade;
    }

    public void setExam_grade(int exam_grade) {
        this.exam_grade = exam_grade;
    }

    public int getFinal_grade() {
        return final_grade;
    }

    public void setFinal_grade(int final_grade) {
        this.final_grade = final_grade;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
