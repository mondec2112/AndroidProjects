package com.example.monsanity.edusoft.container;

import java.util.ArrayList;

/**
 * Created by monsanity on 4/26/18.
 */

public class Exam {
    private String course;
    private String semester;
    private String exam_type;
    private String subject_id;
    private String subject_name;
    private int combined_exam;
    private int exam_team;
    private int quantity;
    private String exam_date;
    private String start_hour;
    private int num_minute;
    private String room;
    private int exam_week;
    private ArrayList<String> student_list;
    private String proctor_id;
    private boolean isActive;

    public Exam() {
    }

    public Exam(String course, String semester, String exam_type, String subject_id, int combined_exam, int exam_team, int quantity, String exam_date, String start_hour, int num_minute, String room, int exam_week, ArrayList<String> student_list, String proctor_id, boolean isActive) {
        this.course = course;
        this.semester = semester;
        this.exam_type = exam_type;
        this.subject_id = subject_id;
        this.combined_exam = combined_exam;
        this.exam_team = exam_team;
        this.quantity = quantity;
        this.exam_date = exam_date;
        this.start_hour = start_hour;
        this.num_minute = num_minute;
        this.room = room;
        this.exam_week = exam_week;
        this.student_list = student_list;
        this.proctor_id = proctor_id;
        this.isActive = isActive;
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

    public String getExam_type() {
        return exam_type;
    }

    public void setExam_type(String exam_type) {
        this.exam_type = exam_type;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public int getCombined_exam() {
        return combined_exam;
    }

    public void setCombined_exam(int combined_exam) {
        this.combined_exam = combined_exam;
    }

    public int getExam_team() {
        return exam_team;
    }

    public void setExam_team(int exam_team) {
        this.exam_team = exam_team;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExam_date() {
        return exam_date;
    }

    public void setExam_date(String exam_date) {
        this.exam_date = exam_date;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public int getNum_minute() {
        return num_minute;
    }

    public void setNum_minute(int num_minute) {
        this.num_minute = num_minute;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getExam_week() {
        return exam_week;
    }

    public void setExam_week(int exam_week) {
        this.exam_week = exam_week;
    }

    public ArrayList<String> getStudent_list() {
        return student_list;
    }

    public void setStudent_list(ArrayList<String> student_list) {
        this.student_list = student_list;
    }

    public String getProctor_id() {
        return proctor_id;
    }

    public void setProctor_id(String proctor_id) {
        this.proctor_id = proctor_id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
}
