package com.example.monsanity.edusoft.adapter;

public class CourseUtils {
    public String current_course;
    public String next_course;
    public int credit_unit;
    public int credit_unit_linking;

    public CourseUtils() {
    }

    public CourseUtils(String current_course, String next_course) {
        this.current_course = current_course;
        this.next_course = next_course;
    }

    public String getCurrent_course() {
        return current_course;
    }

    public void setCurrent_course(String current_course) {
        this.current_course = current_course;
    }

    public String getNext_course() {
        return next_course;
    }

    public void setNext_course(String next_course) {
        this.next_course = next_course;
    }

    public int getCredit_unit() {
        return credit_unit;
    }

    public void setCredit_unit(int credit_unit) {
        this.credit_unit = credit_unit;
    }

    public int getCredit_unit_linking() {
        return credit_unit_linking;
    }

    public void setCredit_unit_linking(int credit_unit_linking) {
        this.credit_unit_linking = credit_unit_linking;
    }
}
