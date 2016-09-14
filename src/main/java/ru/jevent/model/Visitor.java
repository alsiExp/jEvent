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

    public Visitor(String firstName, String lastName, Sex sex, boolean enabled, String photoURL, LocalDateTime birthDay, LocalDate registered, String email, String phone, String gitHubAccount, String linkedInAccount, String twitterAccount, String employer, String biography, String description, double cost, List<Event> visitorEventList, List<Event> speakerEventList, List<Task> visitorTasks, List<Comment> commentList) {
        super(firstName, lastName, sex, enabled, photoURL);
        this.birthDay = birthDay;
        this.registered = registered;
        this.email = email;
        this.phone = phone;
        this.gitHubAccount = gitHubAccount;
        this.linkedInAccount = linkedInAccount;
        this.twitterAccount = twitterAccount;
        this.employer = employer;
        this.biography = biography;
        this.description = description;
        this.cost = cost;
        this.visitorEventList = visitorEventList;
        this.speakerEventList = speakerEventList;
        this.visitorTasks = visitorTasks;
        this.commentList = commentList;
    }

    public Visitor(Long id, String firstName, String lastName, Sex sex, String photoURL, LocalDateTime birthDay, LocalDate registered, String email, String phone, String gitHubAccount, String linkedInAccount, String twitterAccount, String employer, String biography, String description, double cost, List<Event> visitorEventList, List<Event> speakerEventList, List<Task> visitorTasks, List<Comment> commentList) {
        super(id, firstName, lastName, sex, photoURL);
        this.birthDay = birthDay;
        this.registered = registered;
        this.email = email;
        this.phone = phone;
        this.gitHubAccount = gitHubAccount;
        this.linkedInAccount = linkedInAccount;
        this.twitterAccount = twitterAccount;
        this.employer = employer;
        this.biography = biography;
        this.description = description;
        this.cost = cost;
        this.visitorEventList = visitorEventList;
        this.speakerEventList = speakerEventList;
        this.visitorTasks = visitorTasks;
        this.commentList = commentList;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visitor)) return false;
        if (!super.equals(o)) return false;

        Visitor visitor = (Visitor) o;

        if (Double.compare(visitor.cost, cost) != 0) return false;
        if (birthDay != null ? !birthDay.equals(visitor.birthDay) : visitor.birthDay != null) return false;
        if (registered != null ? !registered.equals(visitor.registered) : visitor.registered != null) return false;
        if (email != null ? !email.equals(visitor.email) : visitor.email != null) return false;
        if (phone != null ? !phone.equals(visitor.phone) : visitor.phone != null) return false;
        if (gitHubAccount != null ? !gitHubAccount.equals(visitor.gitHubAccount) : visitor.gitHubAccount != null)
            return false;
        if (linkedInAccount != null ? !linkedInAccount.equals(visitor.linkedInAccount) : visitor.linkedInAccount != null)
            return false;
        if (twitterAccount != null ? !twitterAccount.equals(visitor.twitterAccount) : visitor.twitterAccount != null)
            return false;
        if (employer != null ? !employer.equals(visitor.employer) : visitor.employer != null) return false;
        if (biography != null ? !biography.equals(visitor.biography) : visitor.biography != null) return false;
        if (description != null ? !description.equals(visitor.description) : visitor.description != null) return false;
        if (visitorEventList != null ? !visitorEventList.equals(visitor.visitorEventList) : visitor.visitorEventList != null)
            return false;
        if (speakerEventList != null ? !speakerEventList.equals(visitor.speakerEventList) : visitor.speakerEventList != null)
            return false;
        if (visitorTasks != null ? !visitorTasks.equals(visitor.visitorTasks) : visitor.visitorTasks != null)
            return false;
        return commentList != null ? commentList.equals(visitor.commentList) : visitor.commentList == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
        result = 31 * result + (registered != null ? registered.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (gitHubAccount != null ? gitHubAccount.hashCode() : 0);
        result = 31 * result + (linkedInAccount != null ? linkedInAccount.hashCode() : 0);
        result = 31 * result + (twitterAccount != null ? twitterAccount.hashCode() : 0);
        result = 31 * result + (employer != null ? employer.hashCode() : 0);
        result = 31 * result + (biography != null ? biography.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (visitorEventList != null ? visitorEventList.hashCode() : 0);
        result = 31 * result + (speakerEventList != null ? speakerEventList.hashCode() : 0);
        result = 31 * result + (visitorTasks != null ? visitorTasks.hashCode() : 0);
        result = 31 * result + (commentList != null ? commentList.hashCode() : 0);
        return result;
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
