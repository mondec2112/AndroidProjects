package com.example.monsanity.edusoft.container;

/**
 * Created by monsanity on 4/25/18.
 */

public class StudentGrade {
    String student_id;
    int progress_grade;
    int test_grade;
    int exam_grade;
    int final_grade;
    String rank;
    String subject_name;

    public StudentGrade() {
    }

    public StudentGrade(String student_id, int progress_grade, int test_grade, int exam_grade, int final_grade, String rank) {
        this.student_id = student_id;
        this.progress_grade = progress_grade;
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

    public int getProgress_grade() {
        return progress_grade;
    }

    public void setProgress_grade(int progress_grade) {
        this.progress_grade = progress_grade;
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

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
}
