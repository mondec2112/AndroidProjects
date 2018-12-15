package com.example.monsanity.edusoft.container;

public class SlotContainer {
    private int slot;
    private boolean isEmpty;

    public SlotContainer() {
    }

    public SlotContainer(int slot, boolean isEmpty) {
        this.slot = slot;
        this.isEmpty = isEmpty;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
