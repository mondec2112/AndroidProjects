package com.example.monsanity.edusoft.container;

public class ProfileDetail {
    String label;
    String detail;

    public ProfileDetail(String label, String detail) {
        this.label = label;
        this.detail = detail;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
