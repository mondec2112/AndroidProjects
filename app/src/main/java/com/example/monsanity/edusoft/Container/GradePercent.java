package com.example.monsanity.edusoft.Container;

/**
 * Created by monsanity on 4/25/18.
 */

public class GradePercent {
    int prog_percent;
    int test_percent;
    int exam_percent;

    public GradePercent() {
    }

    public GradePercent(int prog_percent, int test_percent, int exam_percent) {
        this.prog_percent = prog_percent;
        this.test_percent = test_percent;
        this.exam_percent = exam_percent;
    }

    public int getProg_percent() {
        return prog_percent;
    }

    public void setProg_percent(int prog_percent) {
        this.prog_percent = prog_percent;
    }

    public int getTest_percent() {
        return test_percent;
    }

    public void setTest_percent(int test_percent) {
        this.test_percent = test_percent;
    }

    public int getExam_percent() {
        return exam_percent;
    }

    public void setExam_percent(int exam_percent) {
        this.exam_percent = exam_percent;
    }
}
