package com.example.monsanity.edusoft.container;

/**
 * Created by monsanity on 3/27/18.
 */

public class Lecturer {
    String id;
    String name;
    String dob;
    String joined;
    String email;
    String faculty;

    public Lecturer() {
    }

    public Lecturer(String id, String name, String dob, String joined, String email, String faculty) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.joined = joined;
        this.email = email;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getJoined() {
        return joined;
    }

    public void setJoined(String joined) {
        this.joined = joined;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
