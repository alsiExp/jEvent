package ru.jevent.model.Event;

import ru.jevent.model.NamedEntity;

import java.time.LocalDateTime;

public class Slot extends NamedEntity {

    public Slot() {
    }

    private LocalDateTime start;
    private SlotType orderType;

    public enum SlotType {
        CHEK_IN,
        KEYNOTE,
        BREAK,
        LECTURE
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public SlotType getOrderType() {
        return orderType;
    }

    public void setOrderType(SlotType orderType) {
        this.orderType = orderType;
    }
}
