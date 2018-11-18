package com.example.monsanity.edusoft.container;

public class StudentFee {
    private int credit;
    private int tuition_credit;
    private float sem_fee;
    private float paid_fee;
    private float payable_fee;

    public StudentFee() {
    }

    public StudentFee(int credit, int tuition_credit, float sem_fee, float paid_fee, float payable_fee) {
        this.credit = credit;
        this.tuition_credit = tuition_credit;
        this.sem_fee = sem_fee;
        this.paid_fee = paid_fee;
        this.payable_fee = payable_fee;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getTuition_credit() {
        return tuition_credit;
    }

    public void setTuition_credit(int tuition_credit) {
        this.tuition_credit = tuition_credit;
    }

    public float getSem_fee() {
        return sem_fee;
    }

    public void setSem_fee(float sem_fee) {
        this.sem_fee = sem_fee;
    }

    public float getPaid_fee() {
        return paid_fee;
    }

    public void setPaid_fee(float paid_fee) {
        this.paid_fee = paid_fee;
    }

    public float getPayable_fee() {
        return payable_fee;
    }

    public void setPayable_fee(float payable_fee) {
        this.payable_fee = payable_fee;
    }
}
