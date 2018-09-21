package com.example.monsanity.edusoft.container;

/**
 * Created by monsanity on 4/26/18.
 */

public class Exam {
    String date;
    String subject_id;
    String group;
    String room;
    int num_student;
    String instructor_id;
    String class_id;
    String lengh;

    public Exam() {
    }

    public Exam(String date, String subject_id, String group, String room, int num_student, String instructor_id, String class_id, String lengh) {
        this.date = date;
        this.subject_id = subject_id;
        this.group = group;
        this.room = room;
        this.num_student = num_student;
        this.instructor_id = instructor_id;
        this.class_id = class_id;
        this.lengh = lengh;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getNum_student() {
        return num_student;
    }

    public void setNum_student(int num_student) {
        this.num_student = num_student;
    }

    public String getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(String instructor_id) {
        this.instructor_id = instructor_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getLengh() {
        return lengh;
    }

    public void setLengh(String lengh) {
        this.lengh = lengh;
    }
}
