package ru.jevent.model.Event;

import ru.jevent.model.Common.*;
import ru.jevent.model.NamedEntity;
import ru.jevent.model.Visitor.Visitor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

public class Event extends NamedEntity implements Attachable {

    private String tagName;
    private String adress;
    private String description;

    private LinkedList<Visitor> probableSpeakers;
    private ArrayList<Comment> commentList;             // notes for events

    private LinkedList<Rate> rates;
    private LinkedList<Track> tracks;


    //  implements Attachable

    public String getAttachName() {
        return name;
    }

    public String getAttachDescription() {
        return null;
    }

    public String getAttachURL() {
        return null;
    }

    public byte[] getAttachImage() {
        return new byte[0];
    }
}