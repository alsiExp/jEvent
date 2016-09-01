package ru.jevent.model.Event;

import ru.jevent.model.Common.*;
import ru.jevent.model.NamedEntity;
import ru.jevent.model.Team.User;
import ru.jevent.model.Visitor.Visitor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class Event extends NamedEntity {

    private User creator;


    private String tagName;
    private String adress;
    private String description;

    private LinkedList<Visitor> probableSpeakers;
    private ArrayList<Comment> commentList;             // notes for events

    private LinkedList<Rate> rates;
    private LinkedList<Track> tracks;

}