package ru.jevent.model;

import ru.jevent.model.PersonParts.Email;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Visitor extends Person implements Attachable{

    private LocalDateTime birthDay;
    private String gitHubProfile;                   // from UC-3-1
    private String linkedInProfile;                 // from UC-3-1
    private LocalDate registered;
    private String description;

    private ArrayList<Event> eventsList;            // from UC-3-1, initialized from DB
    private ArrayList<Event> speakerEventsList;     // from UC-3-1, initialized from DB
    private double cost;                            //from UC-3-1  quantity of money, that will be received or payed

    private ArrayList<Task> visitorTasks;           // from UC-3-1, initialized from DB
    private ArrayList<Email> visitorLetter;         // from UC-3-1, initialized from DB

    private ArrayList<Comment> commentList;         // like notes for visitor


    //    constructors
    public Visitor() {
    }

    @Override
    public String getAttachName() {
        return this.getFullName();
    }

    @Override
    public String getAttachDescription() {
        return this.getDescription();
    }

    @Override
    public byte[] getAttachImage() {
        return this.getPhoto();
    }


    // getters and setters

    public LocalDateTime getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDateTime birthDay) {
        this.birthDay = birthDay;
    }

    public String getGitHubProfile() {
        return gitHubProfile;
    }

    public void setGitHubProfile(String gitHubProfile) {
        this.gitHubProfile = gitHubProfile;
    }

    public String getLinkedInProfile() {
        return linkedInProfile;
    }

    public void setLinkedInProfile(String linkedInProfile) {
        this.linkedInProfile = linkedInProfile;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public ArrayList<Event> getEventsList() {
        if( eventsList == null) {
            eventsList = new ArrayList<>();
        }
        return eventsList;
    }

    public void setEventsList(ArrayList<Event> eventsList) {
        this.eventsList = eventsList;
    }

    public ArrayList<Event> getSpeakerEventsList() {
        if(speakerEventsList == null) {
            speakerEventsList = new ArrayList<>();
        }
        return speakerEventsList;
    }

    public void setSpeakerEventsList(ArrayList<Event> speakerEventsList) {
        this.speakerEventsList = speakerEventsList;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ArrayList<Task> getVisitorTasks() {
        if(visitorTasks == null) {
            visitorTasks = new ArrayList<>();
        }
        return visitorTasks;
    }

    public void setVisitorTasks(ArrayList<Task> visitorTasks) {
        this.visitorTasks = visitorTasks;
    }

    public ArrayList<Email> getVisitorLetter() {
        if (visitorLetter == null) {
            visitorLetter = new ArrayList<>();
        }
        return visitorLetter;
    }

    public void setVisitorLetter(ArrayList<Email> visitorLetter) {
        this.visitorLetter = visitorLetter;
    }

    public ArrayList<Comment> getCommentList() {
        if(commentList == null) {
            commentList = new ArrayList<>();
        }
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
