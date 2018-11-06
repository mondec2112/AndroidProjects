package com.example.monsanity.edusoft.container;

public class TimeUtils {
    int hour;
    int minute;

    public TimeUtils(int startHour, int startMinute) {
        this.hour = startHour;
        this.minute = startMinute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
