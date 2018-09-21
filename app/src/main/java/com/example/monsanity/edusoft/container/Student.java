package com.example.monsanity.edusoft.container;

/**
 * Created by monsanity on 3/13/18.
 */

public class Student {
    String name;
    String id;
    String gender;
    String faculty;
    String classID;
    String dob;
    String pob;
    String email;
    String course;
    String type;
    String department;
    String advisor;
    int recommended_year;

    public Student() {
    }

    public Student(String name, String id, String gender, String faculty, String classID, String dob, String pob, String email, String course, String type, String department, String advisor, int recommended_year) {
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.faculty = faculty;
        this.classID = classID;
        this.dob = dob;
        this.pob = pob;
        this.email = email;
        this.course = course;
        this.type = type;
        this.department = department;
        this.advisor = advisor;
        this.recommended_year = recommended_year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    public int getRecommended_year() {
        return recommended_year;
    }

    public void setRecommended_year(int recommended_year) {
        this.recommended_year = recommended_year;
    }
}
