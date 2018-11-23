package com.example.monsanity.edusoft.container;

/**
 * Created by monsanity on 3/12/18.
 */

public class Subjects {

    String id;
    String name;
    int credit;
    String pre;
    String level;
    String department;
    String faculty;
    int type;
    int fee;

    public Subjects() {
    }

    public Subjects(String id, String name, int credit, String pre, String level, String department, String faculty, int type) {
        this.id = id;
        this.name = name;
        this.credit = credit;
        this.pre = pre;
        this.level = level;
        this.department = department;
        this.faculty = faculty;
        this.type = type;
    }

    public Subjects(String id, String name, int credit, String department, String faculty) {
        this.id = id;
        this.name = name;
        this.credit = credit;
        this.department = department;
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
