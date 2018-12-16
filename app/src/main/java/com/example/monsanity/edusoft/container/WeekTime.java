package com.example.monsanity.edusoft.container;

import java.util.ArrayList;

public class WeekTime {
    ArrayList<SlotContainer> slotContainers;

    public WeekTime() {
    }

    public WeekTime(ArrayList<SlotContainer> slotContainers) {
        this.slotContainers = slotContainers;
    }

    public ArrayList<SlotContainer> getSlotContainers() {
        return slotContainers;
    }

    public void setSlotContainers(ArrayList<SlotContainer> slotContainers) {
        this.slotContainers = slotContainers;
    }
}
