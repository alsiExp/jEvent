package ru.jevent.model;

import ru.jevent.model.Enums.SlotType;
import ru.jevent.model.converter.SlotTypeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "slots")
public class Slot extends NamedEntity {

    //    can be null
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id")
    private Visitor approvedSpeaker;
    @Column(name = "slot_description")
    private String slotDescription;
    @Column(name = "start")
    private LocalDateTime start;
    @Column(name = "slot_type")
    @Convert(converter = SlotTypeConverter.class)
    private SlotType slotType;

    //    will be set after Event (by Visitors votes)
    @Column(name = "grade")
    private Integer grade;

    //    invitation costs
    @Column(name = "price")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "track_id")
    private Track track;

    public Slot() {
    }

    public Slot(String name, Visitor approvedSpeaker, String slotDescription, LocalDateTime start,
                SlotType slotType, int grade, int price, Track track) {
        super(name);
        this.approvedSpeaker = approvedSpeaker;
        this.slotDescription = slotDescription;
        this.start = start;
        this.slotType = slotType;
        this.grade = grade;
        this.price = price;
        this.track = track;
    }

    public Slot(long id, String name, Visitor approvedSpeaker, String slotDescription, LocalDateTime start,
                SlotType slotType, int grade, int price, Track track) {
        super(id, name);
        this.approvedSpeaker = approvedSpeaker;
        this.slotDescription = slotDescription;
        this.start = start;
        this.slotType = slotType;
        this.grade = grade;
        this.price = price;
        this.track = track;
    }

    public Visitor getApprovedSpeaker() {
        return approvedSpeaker;
    }

    public void setApprovedSpeaker(Visitor approvedSpeaker) {
        this.approvedSpeaker = approvedSpeaker;
    }

    public String getSlotDescription() {
        return slotDescription;
    }

    public void setSlotDescription(String slotDescription) {
        this.slotDescription = slotDescription;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
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
        if (slotDescription != null ? !slotDescription.equals(slot.slotDescription) : slot.slotDescription != null)
            return false;
        if (start != null ? !start.equals(slot.start) : slot.start != null) return false;
        if (track != null ? !track.getId().equals(track.getId()) : slot.track != null) return false;
        return slotType == slot.slotType;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (approvedSpeaker != null ? approvedSpeaker.hashCode() : 0);
        result = 31 * result + (slotDescription != null ? slotDescription.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (slotType != null ? slotType.hashCode() : 0);
        result = 31 * result + grade;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (track != null ? track.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String speaker = "";
        if(approvedSpeaker != null) {
            speaker  = approvedSpeaker.toString();
        }
        String trackName = "";
        if(track != null) {
            trackName  = track.getName();
        }
        return "Slot{" +
                super.toString() +
                ", approvedSpeakerId=" + speaker +
                ", slotDescription='" + slotDescription + '\'' +
                ", start=" + start +
                ", slotType=" + slotType +
                ", grade=" + grade +
                ", price=" + price +
                ", track=" + trackName +
                "} ";
    }
}
