package com.example.monsanity.edusoft.container;

import java.util.ArrayList;

public class Schedule {
    String student_id;
    ArrayList<String> subject_list;

    public Schedule() {
    }

    public Schedule(String student_id, ArrayList<String> subject_list) {
        this.student_id = student_id;
        this.subject_list = subject_list;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public ArrayList<String> getSubject_list() {
        return subject_list;
    }

    public void setSubject_list(ArrayList<String> subject_list) {
        this.subject_list = subject_list;
    }
}
