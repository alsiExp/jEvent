package ru.jevent.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.jevent.model.additionalEntity.*;
import ru.jevent.model.superclasses.BaseEntity;
import ru.jevent.model.superclasses.Person;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "participants")
@NamedQueries({
        @NamedQuery(name = Participant.DELETE, query = "DELETE from Participant p where p.id = :id"),
        @NamedQuery(name = Participant.ALL_SORTED, query = "SELECT p FROM Participant p ORDER BY p.registered"),
        @NamedQuery(name = Participant.BY_EMAIL, query = "SELECT p FROM Participant p JOIN p.emails e WHERE e.email = ?1"),
})
public class Participant extends Person {
    /*
        fields from jira:
        skype - Skype
        city - Country, City
        travelHelp - Travel

     */

    public static final String DELETE = "Participant.delete";
    public static final String ALL_SORTED = "Participant.getAllSorted";
    public static final String BY_EMAIL = "Participant.getByEmail";

    @ManyToMany(mappedBy = "speakers", cascade = CascadeType.ALL)
    @JsonManagedReference
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Speech> speechSet;

    //    Dates
    @Column(name = "birthday")
    private LocalDateTime birthDay;
    @Column(name = "registered_date", nullable = false)
    private LocalDateTime registered;


    //    contact information & social networks
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Email> emails;
    @Column(name = "phone")
    private String phone;
    @Column(name = "skype")
    private String skype;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "owner_id")
    private GitHub gitHub;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "owner_id")
    private Twitter twitter;


    //    Common info about participant
    @Column(name = "city")
    private String city;
    @Column(name = "employer")
    private String employer;
    @Column(name = "biography")
    private String biography;
    @Column(name = "description")
    private String description;
    @Column(name = "travel_help")
    private String travelHelp;

    @OneToMany(mappedBy = "participant", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("date")
    @JsonManagedReference
    private List<ParticipantComment> commentList;


    public Participant() {
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
        if (this.emails == null) {
            emails = new HashSet<>();
        }
        return emails;
    }

    public void addEmail(Email email) {
        this.getEmails().add(email);
    }

    public void setEmails(Set<Email> emails) {
        if (emails != null && !emails.isEmpty()) {
            if (this.getEmails().isEmpty()) {
                for(Email e : emails) {
                    e.setOwner(this);
                }
                this.emails = emails;
            }
        }
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public GitHub getGitHub() {
        return gitHub;
    }

    public void setGitHub(GitHub gitHub) {
        this.gitHub = gitHub;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
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

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTravelHelp() {
        return travelHelp;
    }

    public void setTravelHelp(String travelHelp) {
        this.travelHelp = travelHelp;
    }

    public List<ParticipantComment> getCommentList() {
        if (commentList == null) {
            commentList = new ArrayList<>();
        }
        return commentList;
    }

    public void setCommentList(List<ParticipantComment> commentList) {
        this.commentList = commentList;
    }


    public Map<Long,String> getParticipantTags() {
        Map<Long,String> map = new HashMap<>();
        speechSet.forEach(speech -> map.putAll( speech.getTags().stream().collect(Collectors.toMap(BaseEntity::getId, SpeechTag::getTag))));
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant)) return false;
        if (!super.equals(o)) return false;

        Participant that = (Participant) o;

        if (birthDay != null ? !birthDay.equals(that.birthDay) : that.birthDay != null) return false;
        if (registered != null ? !registered.equals(that.registered) : that.registered != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (skype != null ? !skype.equals(that.skype) : that.skype != null) return false;
        if (gitHub != null ? !gitHub.equals(that.gitHub) : that.gitHub != null) return false;
        if (twitter != null ? !twitter.equals(that.twitter) : that.twitter != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (employer != null ? !employer.equals(that.employer) : that.employer != null) return false;
        if (biography != null ? !biography.equals(that.biography) : that.biography != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (travelHelp != null ? !travelHelp.equals(that.travelHelp) : that.travelHelp != null) return false;
        if (!isEquals(emails, that.emails))
            return false;
        return this.getCommentList().size() == that.getCommentList().size();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
        result = 31 * result + (registered != null ? registered.hashCode() : 0);
        result = 31 * result + (emails != null ? emails.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (skype != null ? skype.hashCode() : 0);
        result = 31 * result + (gitHub != null ? gitHub.hashCode() : 0);
        result = 31 * result + (twitter != null ? twitter.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (employer != null ? employer.hashCode() : 0);
        result = 31 * result + (biography != null ? biography.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (travelHelp != null ? travelHelp.hashCode() : 0);
        result = 31 * result + (commentList != null ? commentList.size() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder emailsSB = new StringBuilder();
        if (!this.getEmails().isEmpty()) {
            String prefix = "";
            emailsSB.append('[');
            for (Email e : emails) {
                emailsSB.append(prefix);
                prefix = ",";
                emailsSB.append((e).toString());
            }
            emailsSB.append(']');
        }

        return "Participant{" +
                super.toString() +
                "full name=" + this.getFullName() +
                ", registered=" + registered +
                ", email='" + emailsSB.toString() + '\'' +
                ", phone='" + phone + '\'' +
                ", gitHubAccount='" + gitHub + '\'' +
                ", twitterAccount='" + twitter + '\'' +
                ", employer='" + employer + '\'' +
                "} ";
    }
}
