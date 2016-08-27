package ru.jevent.model.Visitor;

import ru.jevent.model.Activities.Activities;
import ru.jevent.model.BaseEntity;
import ru.jevent.model.Common.*;
import ru.jevent.model.Event.Event;
import ru.jevent.model.NamedEntity;
import ru.jevent.model.Task.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Visitor extends NamedEntity implements Attachable {

    private Sex sex;                                // from UC-3-1
    private LocalDateTime birthDay;
    private String gitHubProfile;                   // from UC-3-1
    private String linkedInProfile;                 // from UC-3-1
    private Date registered = new Date();

    private Phone primaryPhone;                     // from UC-3-1
    private ArrayList<Phone> additionalPhones;      // from UC-3-8

    private Email primaryEmail;                     // from UC-3-1
    private ArrayList<Email> additionalEmails;      // from UC-3-8

    private String primaryPhotoURL;                 // string - to store images on special server directory
    private ArrayList<String> additionalPhotoURLs;  //

    private ArrayList<Event> eventsList;            // from UC-3-1, initialized from DB
    private ArrayList<Event> speakerEventsList;     // from UC-3-1, initialized from DB
    private double cost;                            //from UC-3-1  quantity of money, that will be received or payed

    private ArrayList<Task> visitorTasks;           // from UC-3-1, initialized from DB
    private ArrayList<Email> visitorLetter;         // from UC-3-1, initialized from DB

    private ArrayList<Comment> commentList;         // like notes for visitor


    private Activities activities;

    public static enum Sex {
        MALE, FEMALE
    }

    //    constructors
    public Visitor() {

    }




    //     implements Attachable

    public String getAttachName() {
        return null;
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

    public Sex getSex() {
        return sex;
    }



    public void setSex(Sex sex) {
        this.sex = sex;
    }

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

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Phone getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(Phone primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public ArrayList<Phone> getAdditionalPhones() {
        return additionalPhones;
    }

    public void setAdditionalPhones(ArrayList<Phone> additionalPhones) {
        this.additionalPhones = additionalPhones;
    }

    public Email getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(Email primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public ArrayList<Email> getAdditionalEmails() {
        return additionalEmails;
    }

    public void setAdditionalEmails(ArrayList<Email> additionalEmails) {
        this.additionalEmails = additionalEmails;
    }

    public String getPrimaryPhotoURL() {
        return primaryPhotoURL;
    }

    public void setPrimaryPhotoURL(String primaryPhotoURL) {
        this.primaryPhotoURL = primaryPhotoURL;
    }

    public ArrayList<String> getAdditionalPhotoURLs() {
        return additionalPhotoURLs;
    }

    public void setAdditionalPhotoURLs(ArrayList<String> additionalPhotoURLs) {
        this.additionalPhotoURLs = additionalPhotoURLs;
    }

    public ArrayList<Event> getEventsList() {
        return eventsList;
    }

    public void setEventsList(ArrayList<Event> eventsList) {
        this.eventsList = eventsList;
    }

    public ArrayList<Event> getSpeakerEventsList() {
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
        return visitorTasks;
    }

    public void setVisitorTasks(ArrayList<Task> visitorTasks) {
        this.visitorTasks = visitorTasks;
    }

    public ArrayList<Email> getVisitorLetter() {
        return visitorLetter;
    }

    public void setVisitorLetter(ArrayList<Email> visitorLetter) {
        this.visitorLetter = visitorLetter;
    }

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }

    public Activities getActivities() {
        return activities;
    }

    public void setActivities(Activities activities) {
        this.activities = activities;
    }
}
