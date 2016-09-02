package ru.jevent.model;

import ru.jevent.model.Enums.SlotType;
import ru.jevent.model.NamedEntity;

import java.time.LocalDateTime;

public class Slot extends NamedEntity {

    public Slot() {
    }

    private LocalDateTime start;
    private SlotType slotType;

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public void setSlotType(SlotType slotType) {
        this.slotType = slotType;
    }
}
