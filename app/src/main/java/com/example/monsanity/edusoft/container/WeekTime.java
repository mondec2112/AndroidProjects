package com.example.monsanity.edusoft.container;

import java.util.ArrayList;

public class WeekTime {
    String dayOfWeek;
    ArrayList<SlotContainer> slotContainers;

    public WeekTime() {
    }

    public WeekTime(String dayOfWeek, ArrayList<SlotContainer> slotContainers) {
        this.dayOfWeek = dayOfWeek;
        this.slotContainers = slotContainers;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public ArrayList<SlotContainer> getSlotContainers() {
        return slotContainers;
    }

    public void setSlotContainers(ArrayList<SlotContainer> slotContainers) {
        this.slotContainers = slotContainers;
    }
}
