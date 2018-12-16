package com.example.monsanity.edusoft.container;

public class SlotContainer {
    private int slot;
    private boolean isTaken;

    public SlotContainer() {
    }

    public SlotContainer(int slot, boolean isTaken) {
        this.slot = slot;
        this.isTaken = isTaken;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}
