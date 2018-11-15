package com.example.monsanity.edusoft.container;

public class SumGrade {
    private String student_id;
    private float average_100;
    private float average_4;
    private int credits_gained;
    private String rank;

    public SumGrade() {
    }

    public SumGrade(String student_id, float average_100, float average_4, int credits_gained, String rank) {
        this.student_id = student_id;
        this.average_100 = average_100;
        this.average_4 = average_4;
        this.credits_gained = credits_gained;
        this.rank = rank;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public float getAverage_100() {
        return average_100;
    }

    public void setAverage_100(float average_100) {
        this.average_100 = average_100;
    }

    public float getAverage_4() {
        return average_4;
    }

    public void setAverage_4(float average_4) {
        this.average_4 = average_4;
    }

    public int getCredits_gained() {
        return credits_gained;
    }

    public void setCredits_gained(int credits_gained) {
        this.credits_gained = credits_gained;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
