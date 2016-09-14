package ru.jevent.model;

import java.util.LinkedList;
import java.util.List;

public class Track extends NamedEntity {

    private String description;
    // sort by LocalDateTime start
    private List<Slot> slotOrder;


    public Track() {
    }

    public Track(String name, String description, List<Slot> slotOrder) {
        super(name);
        this.description = description;
        this.slotOrder = slotOrder;
    }

    public Track(long id, String name, String description, List<Slot> slotOrder) {
        super(id, name);
        this.description = description;
        this.slotOrder = slotOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Slot> getSlotOrder() {
        if(slotOrder == null) {
            slotOrder = new LinkedList<>();
        }
        return slotOrder;
    }

    public void setSlotOrder(LinkedList<Slot> slotOrder) {
        this.slotOrder = slotOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track)) return false;
        if (!super.equals(o)) return false;

        Track track = (Track) o;

        if (description != null ? !description.equals(track.description) : track.description != null) return false;
        return slotOrder != null ? slotOrder.equals(track.slotOrder) : track.slotOrder == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (slotOrder != null ? slotOrder.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Track{" +
                super.toString() +
                ", description='" + description + '\'' +
                ", slotOrder=" + slotOrder +
                "} " ;
    }
}
