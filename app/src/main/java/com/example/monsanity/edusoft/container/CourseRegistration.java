package com.example.monsanity.edusoft.container;

public class CourseRegistration {
    private String subject_id;
    private String class_id;
    private String group;
    private boolean isActive;
    private String subject_name;
    private int credit;

    public CourseRegistration() {
    }

    public CourseRegistration(String subject_id, String class_id, boolean isActive) {
        this.subject_id = subject_id;
        this.class_id = class_id;
        this.isActive = isActive;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
