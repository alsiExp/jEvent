package ru.jevent.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.jevent.model.additionalEntity.Email;
import ru.jevent.model.additionalEntity.GitHub;
import ru.jevent.model.additionalEntity.SpeechTag;
import ru.jevent.model.additionalEntity.Twitter;
import ru.jevent.model.superclasses.BaseEntity;
import ru.jevent.model.superclasses.Person;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "participants")
@NamedQueries({
        @NamedQuery(name = Participant.DELETE, query = "DELETE from Participant p where p.id = :id"),
        @NamedQuery(name = Participant.ALL_SORTED, query = "SELECT p FROM Participant p ORDER BY p.registered"),
        @NamedQuery(name = Participant.BY_EMAIL, query = "SELECT p FROM Participant p JOIN p.emails e WHERE e.email = ?1"),
        @NamedQuery(name = Participant.BY_TAG, query = "SELECT DISTINCT p FROM Participant p JOIN p.speechSet ss join ss.tags t WHERE t.id = ?1")
})
public class Participant extends Person {
    /*
        fields from jira:
        firstName + lastName - Name
        fullNameEN - nameEN
        employer - Company
        photoURL - Photo link
        Set<Email>(isMain == true) - Email
        phone - Phone
        twitter - Twitter [followers count - optional]
        skype - Skype
        city - Country, City
        travelHelp - Travel
        biography - Bio
        biographyEN - Bio en
        speakerBackground - Speaker background

     */

    public static final String DELETE = "Participant.delete";
    public static final String ALL_SORTED = "Participant.getAllSorted";
    public static final String BY_EMAIL = "Participant.getByEmail";
    public static final String BY_TAG = "Participant.getByTag";

    @Column(name = "full_name_en")
    private String fullNameEN;

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
    @JoinColumn(name="github_id")
    @JsonManagedReference
    private GitHub gitHub;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="twitter_id")
    @JsonManagedReference
    private Twitter twitter;


    //    Common info about participant
    @Column(name = "city")
    private String city;
    @Column(name = "employer")
    private String employer;
    @Column(name = "biography")
    private String biography;
    @Column(name = "biography_en")
    private String biographyEN;
    @Column(name = "travel_help")
    private String travelHelp;
    @Column(name = "background")
    private String speakerBackground;

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
        gitHub.setOwner(this);
        this.gitHub = gitHub;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(Twitter twitter) {
        twitter.setOwner(this);
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

    public String getFullNameEN() {
        return fullNameEN;
    }

    public void setFullNameEN(String fullNameEN) {
        this.fullNameEN = fullNameEN;
    }

    public String getBiographyEN() {
        return biographyEN;
    }

    public void setBiographyEN(String biographyEN) {
        this.biographyEN = biographyEN;
    }

    public String getSpeakerBackground() {
        return speakerBackground;
    }

    public void setSpeakerBackground(String speakerBackground) {
        this.speakerBackground = speakerBackground;
    }


    public Set<Speech> getSpeechSet() {
        if(speechSet == null) {
            speechSet = new HashSet<>();
        }
        return speechSet;
    }

    public void setSpeechSet(Set<Speech> speechSet) {
        this.speechSet = speechSet;
    }

    public void addSpeech(Speech speech) {
        if(speech != null) {
            getSpeechSet().add(speech);
        }
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
        if (biographyEN != null ? !biographyEN.equals(that.biographyEN) : that.biographyEN != null) return false;
        if (fullNameEN != null ? !fullNameEN.equals(that.fullNameEN) : that.fullNameEN != null) return false;
        if (speakerBackground != null ? !speakerBackground.equals(that.speakerBackground) : that.speakerBackground != null) return false;
        if (travelHelp != null ? !travelHelp.equals(that.travelHelp) : that.travelHelp != null) return false;
        if (!isEquals(emails, that.emails))
            return false;
        return (isEquals(speechSet, that.speechSet));
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
        result = 31 * result + (biographyEN != null ? biographyEN.hashCode() : 0);
        result = 31 * result + (fullNameEN != null ? fullNameEN.hashCode() : 0);
        result = 31 * result + (speakerBackground != null ? speakerBackground.hashCode() : 0);
        result = 31 * result + (travelHelp != null ? travelHelp.hashCode() : 0);
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
        StringBuilder gh = new StringBuilder();
        if(gitHub != null) {
            gh.append('\'').append(gitHub.getFullLink()).append('\'');
        }
        StringBuilder tw = new StringBuilder();
        if(twitter != null) {
            tw.append('\'').append(twitter.getFullLink()).append('\'');
        }


        return "Participant{" +
                super.toString() +
                "full name=" + this.getFullName() +
                ", registered=" + registered +
                ", email='" + emailsSB.toString() + '\'' +
                ", phone='" + phone + '\'' +
                ", gitHubAccount=" + gh.toString() +
                ", twitterAccount=" + tw.toString() +
                ", employer='" + employer + '\'' +
                "} ";
    }
}
