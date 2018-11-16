package com.example.monsanity.edusoft.container;

public class RegisteredSubject {
    String reg_sub;
    String subject_id;
    String course;
    String semester;
    String subject_name;

    public RegisteredSubject() {
    }

    public RegisteredSubject(String reg_sub, String subject_id) {
        this.reg_sub = reg_sub;
        this.subject_id = subject_id;
    }

    public RegisteredSubject(String reg_sub, String subject_id, String course, String semester) {
        this.reg_sub = reg_sub;
        this.subject_id = subject_id;
        this.course = course;
        this.semester = semester;
    }

    public String getReg_sub() {
        return reg_sub;
    }

    public void setReg_sub(String reg_sub) {
        this.reg_sub = reg_sub;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
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

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
}
