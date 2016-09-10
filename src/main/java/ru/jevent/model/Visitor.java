package ru.jevent.model;

import ru.jevent.model.Enums.Sex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Visitor extends Person implements Attachable {

    //    Dates
    private LocalDateTime birthDay;
    private LocalDate registered;

    //    connection info
    private String email;
    private String phone;

    //    Social Networks
    private String gitHubAccount;
    private String linkedInAccount;
    private String twitterAccount;

    //    Visitor description
    private String employer;
    private String biography;
    private String description;

    //    quantity of money, that will be received or payed
    private double cost;


    //    Our events
    //    only visitor, not include speakerEventList
    //    TODO: later create object to store in DB date of buy and maybe something else
    private List<Event> visitorEventList;

    //    events, where Visitor was speaker
    //
    private List<Event> speakerEventList;



    //     related Tasks
    private List<Task> visitorTasks;

    //    notes from all Users about Visitor
    private List<Comment> commentList;

    /*
    After version 0.9:
        - add storage for Emails
        - additional phones and emails list ??

     */


    public Visitor() {
    }

    public Visitor(Long id, String firstName, String lastName, LocalDate registered) {
        super(id, firstName, lastName);
        this.registered = registered;
    }

    public Visitor(Long id, String firstName, String lastName, Sex sex, String photoURL, LocalDate registered) {
        super(id, firstName, lastName, sex, photoURL);
        this.registered = registered;
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
    public String getAttachImageURL() {
        return this.getPhotoURL();
    }

    public LocalDateTime getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDateTime birthDay) {
        this.birthDay = birthDay;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getGitHubAccount() {
        return gitHubAccount;
    }

    public void setGitHubAccount(String gitHubAccount) {
        this.gitHubAccount = gitHubAccount;
    }

    public String getLinkedInAccount() {
        return linkedInAccount;
    }

    public void setLinkedInAccount(String linkedInAccount) {
        this.linkedInAccount = linkedInAccount;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public void setTwitterAccount(String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }



    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }



    public List<Event> getVisitorEventList() {
        if (visitorEventList == null) {
            visitorEventList = new ArrayList<>();
        }
        return visitorEventList;
    }

    public void setVisitorEventList(ArrayList<Event> visitorEventList) {
        this.visitorEventList = visitorEventList;
    }

    public List<Event> getSpeakerEventList() {
        if (speakerEventList == null) {
            speakerEventList = new ArrayList<>();
        }
        return speakerEventList;
    }

    public void setSpeakerEventList(ArrayList<Event> speakerEventList) {
        this.speakerEventList = speakerEventList;
    }



    public List<Task> getVisitorTasks() {
        if (visitorTasks == null) {
            visitorTasks = new ArrayList<>();
        }
        return visitorTasks;
    }

    public void setVisitorTasks(ArrayList<Task> visitorTasks) {
        this.visitorTasks = visitorTasks;
    }



    public List<Comment> getCommentList() {
        if (commentList == null) {
            commentList = new ArrayList<>();
        }
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }



    @Override
    public String toString() {
        return "Visitor{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                '}';
    }
}
