package ru.jevent.model;

import ru.jevent.model.Enums.SlotType;

import java.time.LocalDateTime;

public class Slot extends NamedEntity {

    //    can be null
    private Visitor approvedSpeaker;
    private String lectureDescription;
    private LocalDateTime start;
    private SlotType slotType;
    //    will be set after Event (by Visitors votes)
    private int grade;
    //    invitation costs
    private int price;

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


    public Visitor getApprovedSpeaker() {
        return approvedSpeaker;
    }

    public void setApprovedSpeaker(Visitor approvedSpeaker) {
        this.approvedSpeaker = approvedSpeaker;
    }

    public String getLectureDescription() {
        return lectureDescription;
    }

    public void setLectureDescription(String lectureDescription) {
        this.lectureDescription = lectureDescription;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
