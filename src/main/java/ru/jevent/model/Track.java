package ru.jevent.model;

import ru.jevent.model.NamedEntity;
import ru.jevent.model.Slot;
import ru.jevent.model.Visitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Track extends NamedEntity {

    private LinkedList<Slot> slotOrder;
    private Map<Slot, Visitor> approvedSpeakers;

    public Track() {
    }

    public LinkedList<Slot> getOrder() {
        if(slotOrder == null) {
            slotOrder = new LinkedList<>();
        }
        return slotOrder;
    }

    public void setOrder(LinkedList<Slot> slotOrder) {
        this.slotOrder = slotOrder;
    }

    public Map<Slot, Visitor> getApprovedSpeakers() {
        if(approvedSpeakers == null)
            approvedSpeakers = new HashMap<>();
        return approvedSpeakers;
    }

    public void setApprovedSpeakers(Map<Slot, Visitor> approvedSpeakers) {
        this.approvedSpeakers = approvedSpeakers;
    }
}
