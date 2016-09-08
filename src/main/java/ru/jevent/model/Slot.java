package ru.jevent.model;

import ru.jevent.model.Enums.SlotType;

import java.time.LocalDateTime;

public class Slot extends NamedEntity {

    public Slot() {
    }

    public Slot(long id, String name, LocalDateTime start, SlotType slotType) {
        super(id, name);
        this.start = start;
        this.slotType = slotType;
    }

    public Slot(long id, String name, Visitor speaker, LocalDateTime start, SlotType slotType) {
        super(id, name);
        this.approvedSpeaker = speaker;
        this.start = start;
        this.slotType = slotType;
    }

    //    can be null
    private Visitor approvedSpeaker;
    private LocalDateTime start;
    private SlotType slotType;
    //    set after Event by Visitors votes
    private int grade;

    public Visitor getApprovedSpeaker() {
        return approvedSpeaker;
    }

    public void setApprovedSpeaker(Visitor approvedSpeaker) {
        this.approvedSpeaker = approvedSpeaker;
    }

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
