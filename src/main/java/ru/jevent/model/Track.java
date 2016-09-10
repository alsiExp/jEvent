package ru.jevent.model;

import java.util.LinkedList;
import java.util.List;

public class Track extends NamedEntity {

    private String description;
    // sort by LocalDateTime start
    private List<Slot> slotOrder;


    public Track() {
    }

    public Track(long id, String name, String description) {
        super(id, name);
        this.description = description;
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
}
