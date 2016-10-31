package ru.jevent.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "visitors")
@NamedQueries({
        @NamedQuery(name = Visitor.DELETE, query = "DELETE from Visitor v where v.id = :id"),
        @NamedQuery(name = Visitor.ALL_SORTED, query = "SELECT v FROM Visitor v ORDER BY v.registered"),
        //@NamedQuery(name = Visitor.BY_EMAIL, query = "SELECT v FROM Visitor v WHERE v.email = ?1"),
})
public class Visitor extends Person {
    /*
        Entity Visitor have not links for Event and Task.
        To find Events, where Visitor was speaker, use getSpeakerEventList<Visitor>(long id) in EventRepository;
        To find Events, where Visitor was only visitor, use getVisitorEventList<Visitor>(long id) in EventRepository;
        To find all Tasks for visitor use TaskRepository

         After version 0.9:
        - add storage for Emails
        - additional phones and emails list ??

     */

    public static final String DELETE = "Visitor.delete";
    public static final String ALL_SORTED = "Visitor.getAllSorted";
    //public static final String BY_EMAIL = "Visitor.getByEmail";

    //    Dates
    @Column(name = "birthday")
    private LocalDateTime birthDay;
    @Column(name = "registered_date", nullable = false)
    private LocalDateTime registered;

    //    connection info

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Email> emails;
    @Column(name = "phone")
    private String phone;

    //    Social Networks
    @Column(name = "github_account")
    private String gitHubAccount;
    @Column(name = "linkedin_account")
    private String linkedInAccount;
    @Column(name = "twitter_account")
    private String twitterAccount;

    //    Visitor description
    @Column(name = "employer")
    private String employer;
    @Column(name = "biography")
    private String biography;
    @Column(name = "description")
    private String description;

    //    quantity of money, that will be received or payed
    @Column(name = "cost")
    private double cost;

    //    notes from all Users about Visitor
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "visitors_comments",
            joinColumns = @JoinColumn(name = "visitor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id", unique = true))
    @OrderBy("date")
    private List<Comment> commentList;


    public Visitor() {
    }

    public LocalDateTime getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDateTime birthDay) {
        this.birthDay = birthDay;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }


    public Set<Email> getEmails() {
        if(this.emails == null) {
            return new HashSet<>();
        }
        else {
            return emails;
        }
    }

    public void addEmail(Email email) {
        this.getEmails().add(email);
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

    public List<Comment> getCommentList() {
        if (commentList == null) {
            commentList = new ArrayList<>();
        }
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
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
        if (!isEquals(this.getEmails(), visitor.getEmails())) {
            return false;
        }
        return isEquals(this.getCommentList(), visitor.getCommentList());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
        result = 31 * result + (registered != null ? registered.hashCode() : 0);
        result = 31 * result + (emails != null ? emails.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (gitHubAccount != null ? gitHubAccount.hashCode() : 0);
        result = 31 * result + (linkedInAccount != null ? linkedInAccount.hashCode() : 0);
        result = 31 * result + (twitterAccount != null ? twitterAccount.hashCode() : 0);
        result = 31 * result + (employer != null ? employer.hashCode() : 0);
        result = 31 * result + (biography != null ? biography.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (commentList != null ? commentList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder commentsSB = new StringBuilder();
        StringBuilder emailsSB = new StringBuilder();
        if (!this.getCommentList().isEmpty()) {
            String prefix = "";
            commentsSB.append('[');
            for (Comment c : commentList) {
                commentsSB.append(prefix);
                prefix = ",";
                commentsSB.append((c).toString());
            }
            commentsSB.append(']');
        }
        if(!this.getEmails().isEmpty()){
            String prefix = "";
            emailsSB.append('[');
            for (Email e : emails) {
                emailsSB.append(prefix);
                prefix = ",";
                emailsSB.append((e).toString());
            }
            commentsSB.append(']');
        }

        return "Visitor{" +
                super.toString() +
                "birthDay=" + birthDay +
                ", registered=" + registered +
                ", email='" + emailsSB.toString() + '\'' +
                ", phone='" + phone + '\'' +
                ", gitHubAccount='" + gitHubAccount + '\'' +
                ", linkedInAccount='" + linkedInAccount + '\'' +
                ", twitterAccount='" + twitterAccount + '\'' +
                ", employer='" + employer + '\'' +
                ", biography='" + biography + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", commentList=" + commentsSB.toString() +
                "} ";
    }
}
