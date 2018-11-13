package com.example.monsanity.edusoft.container;

public class RegisteredSubject {
    String reg_sub;
    String subject_id;

    public RegisteredSubject() {
    }

    public RegisteredSubject(String reg_sub, String subject_id) {
        this.reg_sub = reg_sub;
        this.subject_id = subject_id;
    }

    public String getReg_sub() {
        return reg_sub;
    }

    public void setReg_sub(String reg_sub) {
        this.reg_sub = reg_sub;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }
}
