package com.example.monsanity.edusoft.container;

import java.util.ArrayList;

/**
 * Created by monsanity on 4/25/18.
 */

public class ClassGrade {
    private int progress_percent;
    private int test_percent;
    private int exam_percent;
    private String course;
    private String semester;
    private String class_id;
    private String subject_id;
    private ArrayList<StudentGrade> grades;

    public ClassGrade() {
    }

    public ClassGrade(int progress_percent,
                      int test_percent,
                      int exam_percent,
                      String course,
                      String semester,
                      String class_id,
                      String subject_id,
                      ArrayList<StudentGrade> grades) {
        this.progress_percent = progress_percent;
        this.test_percent = test_percent;
        this.exam_percent = exam_percent;
        this.course = course;
        this.semester = semester;
        this.class_id = class_id;
        this.subject_id = subject_id;
        this.grades = grades;
    }

    public int getProgress_percent() {
        return progress_percent;
    }

    public void setProgress_percent(int progress_percent) {
        this.progress_percent = progress_percent;
    }

    public int getTest_percent() {
        return test_percent;
    }

    public void setTest_percent(int test_percent) {
        this.test_percent = test_percent;
    }

    public int getExam_percent() {
        return exam_percent;
    }

    public void setExam_percent(int exam_percent) {
        this.exam_percent = exam_percent;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public ArrayList<StudentGrade> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<StudentGrade> grades) {
        this.grades = grades;
    }
}
