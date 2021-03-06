package com.example.monsanity.edusoft.container;

import java.util.ArrayList;

/**
 * Created by monsanity on 4/25/18.
 */

public class Classes {

    String subject_id;
    int group;
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
    ArrayList<String> class_date;
    ArrayList<String> student_list;
    String subject_name;
    String lecturer_name;
    ArrayList<Classes> lab_lessons;
    String course;
    String semester;
    int credit;

    public Classes(String subject_id, int group, String room, int start_slot, int sum_slot, String instructor_id, String class_id, int class_size, int registered_number, String start_day, String end_day, String day_of_week, ArrayList<String> class_date, ArrayList<String> student_list, ArrayList<Classes> lab_lessons, String course, String semester) {
        this.subject_id = subject_id;
        this.group = group;
        this.room = room;
        this.start_slot = start_slot;
        this.sum_slot = sum_slot;
        this.instructor_id = instructor_id;
        this.class_id = class_id;
        this.class_size = class_size;
        this.registered_number = registered_number;
        this.start_day = start_day;
        this.end_day = end_day;
        this.day_of_week = day_of_week;
        this.class_date = class_date;
        this.student_list = student_list;
        this.lab_lessons = lab_lessons;
        this.course = course;
        this.semester = semester;
    }

    public Classes(String room, int start_slot, int sum_slot, String instructor_id, int class_size, int registered_number, String start_day, String end_day, String day_of_week, ArrayList<String> class_date, ArrayList<String> student_list) {
        this.room = room;
        this.start_slot = start_slot;
        this.sum_slot = sum_slot;
        this.instructor_id = instructor_id;
        this.class_size = class_size;
        this.registered_number = registered_number;
        this.start_day = start_day;
        this.end_day = end_day;
        this.day_of_week = day_of_week;
        this.class_date = class_date;
        this.student_list = student_list;
    }

    public Classes() {

    }

    public ArrayList<String> getStudent_list() {
        return student_list;
    }

    public void setStudent_list(ArrayList<String> student_list) {
        this.student_list = student_list;
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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
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

    public ArrayList<String> getClass_date() {
        return class_date;
    }

    public void setClass_date(ArrayList<String> class_date) {
        this.class_date = class_date;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getLecturer_name() {
        return lecturer_name;
    }

    public void setLecturer_name(String lecturer_name) {
        this.lecturer_name = lecturer_name;
    }

    public ArrayList<Classes> getLab_lessons() {
        return lab_lessons;
    }

    public void setLab_lessons(ArrayList<Classes> lab_lessons) {
        this.lab_lessons = lab_lessons;
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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public class SubClasses{
        private Classes classes;
        private ArrayList<String> class_date;
        private int class_size;
        private int instructor_id;
        private String room;
        int start_slot;
        int sum_slot;

        public SubClasses() {
        }

        public Classes getClasses() {
            return classes;
        }

        public void setClasses(Classes classes) {
            this.classes = classes;
        }

        public ArrayList<String> getClass_date() {
            return class_date;
        }

        public void setClass_date(ArrayList<String> class_date) {
            this.class_date = class_date;
        }

        public int getClass_size() {
            return class_size;
        }

        public void setClass_size(int class_size) {
            this.class_size = class_size;
        }

        public int getInstructor_id() {
            return instructor_id;
        }

        public void setInstructor_id(int instructor_id) {
            this.instructor_id = instructor_id;
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
    }
}
