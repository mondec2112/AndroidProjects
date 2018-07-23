package com.example.monsanity.edusoft.Container;

/**
 * Created by monsanity on 4/26/18.
 */

public class Fee {
    String student_id;
    int sum_credit;
    int sum_fee;

    public Fee() {
    }

    public Fee(String student_id, int sum_credit, int sum_fee) {
        this.student_id = student_id;
        this.sum_credit = sum_credit;
        this.sum_fee = sum_fee;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public int getSum_credit() {
        return sum_credit;
    }

    public void setSum_credit(int sum_credit) {
        this.sum_credit = sum_credit;
    }

    public int getSum_fee() {
        return sum_fee;
    }

    public void setSum_fee(int sum_fee) {
        this.sum_fee = sum_fee;
    }
}
