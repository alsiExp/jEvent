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

    public Slot(String name, Visitor approvedSpeaker, String lectureDescription, LocalDateTime start,
                SlotType slotType, int grade, int price) {
        super(name);
        this.approvedSpeaker = approvedSpeaker;
        this.lectureDescription = lectureDescription;
        this.start = start;
        this.slotType = slotType;
        this.grade = grade;
        this.price = price;
    }

    public Slot(long id, String name, Visitor approvedSpeaker, String lectureDescription, LocalDateTime start,
                SlotType slotType, int grade, int price) {
        super(id, name);
        this.approvedSpeaker = approvedSpeaker;
        this.lectureDescription = lectureDescription;
        this.start = start;
        this.slotType = slotType;
        this.grade = grade;
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Slot)) return false;
        if (!super.equals(o)) return false;

        Slot slot = (Slot) o;

        if (grade != slot.grade) return false;
        if (price != slot.price) return false;
        if (approvedSpeaker != null ? !approvedSpeaker.equals(slot.approvedSpeaker) : slot.approvedSpeaker != null)
            return false;
        if (lectureDescription != null ? !lectureDescription.equals(slot.lectureDescription) : slot.lectureDescription != null)
            return false;
        if (start != null ? !start.equals(slot.start) : slot.start != null) return false;
        return slotType == slot.slotType;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (approvedSpeaker != null ? approvedSpeaker.hashCode() : 0);
        result = 31 * result + (lectureDescription != null ? lectureDescription.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (slotType != null ? slotType.hashCode() : 0);
        result = 31 * result + grade;
        result = 31 * result + price;
        return result;
    }

    @Override
    public String toString() {
        return "Slot{" +
                super.toString() +
                ", approvedSpeakerId=" + approvedSpeaker.toString() +
                ", lectureDescription='" + lectureDescription + '\'' +
                ", start=" + start +
                ", slotType=" + slotType +
                ", grade=" + grade +
                ", price=" + price +
                "} ";
    }
}
