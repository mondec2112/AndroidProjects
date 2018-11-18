package com.example.monsanity.edusoft.adapter;

public class CourseUtils {
    public String current_course;
    public String next_course;

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
}
