package ru.jevent.model;

import java.util.ArrayList;

public class Track extends NamedEntity {

    private String description;
    private ArrayList<Slot> slotOrder;


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

    public ArrayList<Slot> getSlotOrder() {
        if(slotOrder == null) {
            slotOrder = new ArrayList<>();
        }
        return slotOrder;
    }

    public void setSlotOrder(ArrayList<Slot> slotOrder) {
        this.slotOrder = slotOrder;
    }
}
