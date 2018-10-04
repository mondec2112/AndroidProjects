package com.example.monsanity.edusoft.container;

/**
 * Created by monsanity on 4/25/18.
 */

public class Classes {

    String subject_id;
    String group;
    String room;
    int start_slot;
    int sum_slot;
    String instructor_id;
    String class_id;
    int class_size;
    int registered_number;
    String start_day;
    String end_day;
    String day_of_week;

    public Classes(String subject_id, String year, String semester, String group, String day_of_week, String room, int start_slot, int sum_slot, String instructor_id, String class_id, int class_size, int registered_number, String start_day, String end_day) {
        this.subject_id = subject_id;
        this.group = group;
        this.day_of_week = day_of_week;
        this.room = room;
        this.start_slot = start_slot;
        this.sum_slot = sum_slot;
        this.instructor_id = instructor_id;
        this.class_id = class_id;
        this.class_size = class_size;
        this.registered_number = registered_number;
        this.start_day = start_day;
        this.end_day = end_day;
    }

    public Classes() {

    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
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

    public int getStart_slot() {
        return start_slot;
    }

    public void setStart_slot(int start_slot) {
        this.start_slot = start_slot;
    }

    public int getSum_slot() {
        return sum_slot;
    }

    public void setSum_slot(int sum_slot) {
        this.sum_slot = sum_slot;
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

    public int getClass_size() {
        return class_size;
    }

    public void setClass_size(int class_size) {
        this.class_size = class_size;
    }

    public int getRegistered_number() {
        return registered_number;
    }

    public void setRegistered_number(int registered_number) {
        this.registered_number = registered_number;
    }

    public String getStart_day() {
        return start_day;
    }

    public void setStart_day(String start_day) {
        this.start_day = start_day;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }
}
