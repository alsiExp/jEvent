package ru.jevent.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ru.jevent.model.additionalEntity.SpeechComment;
import ru.jevent.model.additionalEntity.SpeechTag;
import ru.jevent.model.superclasses.NamedEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "speeches")
@NamedQueries({
        @NamedQuery(name = Speech.DELETE, query = "DELETE from Speech s where s.id = :id"),
        @NamedQuery(name = Speech.GET_BY_PARTNER, query = "SELECT s FROM Speech s  LEFT JOIN FETCH s.partner p where p.id = :id")
})
public class Speech extends NamedEntity {

    /*
        fields from jira:
        //from speaker form
        name - Talk title
        nameEN - Talk title [second - on english]
        fullDescription - Description
        fullDescriptionEN - Description EN
        viewerValue - Что получат
        focus - Доклад ориентирован
        shortDescription - Short Description
        shortDescriptionEN - Short Description EN

        //from system
        jiraCreationTime
        jiraUpdateTime
        jiraStatus - task status in jira
        jiraLink -  link to task with speech

        additionally fields:
        synchronizationTime - last sync time
        isFromJira - true if speech was import from jira, false if add manually
    */

    public static final String DELETE = "Speech.delete";
    public static final String GET_BY_PARTNER = "Speech.byPartner";


    @Column(name = "name_en")
    private String nameEN;
    @Column(name = "full_desc")
    private String fullDescription;
    @Column(name = "full_desc_en")
    private String fullDescriptionEN;
    @Column(name = "viewer_value")
    private String viewerValue;
    @Column(name = "focus")
    private String focus;
    @Column(name = "short_desc")
    private String shortDescription;
    @Column(name = "short_desc_en")
    private String shortDescriptionEN;

    @Column(name = "jira_creation_time")
    private LocalDateTime jiraCreationTime;
    @Column(name = "jira_update_time")
    private LocalDateTime jiraUpdateTime;
    @Column(name = "jira_status")
    private String jiraStatus;
    @Column(name = "jira_link")
    private String jiraLink;

    @Column(name = "sync_time")
    private LocalDateTime synchronizationTime;
    @Column(name = "is_from_jira")
    private boolean isFromJira;
    @Column(name = "rating")
    private Double rating;

    @Column(name = "speaker_cost")
    private Double speakerCost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id")
    @JsonBackReference
    private Partner partner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private Event event;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "speech_participants",
            joinColumns = @JoinColumn(name = "speech_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id", referencedColumnName = "id", unique = true))
    @JsonBackReference
    private Set<Participant> speakers;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "speeches_speech_tags",
            joinColumns = @JoinColumn(name = "speech_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id", unique = true))
    private Set<SpeechTag> tags;

    @OneToMany(mappedBy = "speech", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("date")
    @JsonManagedReference
    private List<SpeechComment> commentList;

    public Speech() {
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getFullDescriptionEN() {
        return fullDescriptionEN;
    }

    public void setFullDescriptionEN(String fullDescriptionEN) {
        this.fullDescriptionEN = fullDescriptionEN;
    }

    public String getViewerValue() {
        return viewerValue;
    }

    public void setViewerValue(String viewerValue) {
        this.viewerValue = viewerValue;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getShortDescriptionEN() {
        return shortDescriptionEN;
    }

    public void setShortDescriptionEN(String shortDescriptionEN) {
        this.shortDescriptionEN = shortDescriptionEN;
    }

    public LocalDateTime getJiraCreationTime() {
        return jiraCreationTime;
    }

    public void setJiraCreationTime(LocalDateTime jiraCreationTime) {
        this.jiraCreationTime = jiraCreationTime;
    }

    public LocalDateTime getJiraUpdateTime() {
        return jiraUpdateTime;
    }

    public void setJiraUpdateTime(LocalDateTime jiraUpdateTime) {
        this.jiraUpdateTime = jiraUpdateTime;
    }

    public String getJiraStatus() {
        return jiraStatus;
    }

    public void setJiraStatus(String jiraStatus) {
        this.jiraStatus = jiraStatus;
    }

    public String getJiraLink() {
        return jiraLink;
    }

    public void setJiraLink(String jiraLink) {
        this.jiraLink = jiraLink;
    }

    public LocalDateTime getSynchronizationTime() {
        return synchronizationTime;
    }

    public void setSynchronizationTime(LocalDateTime synchronizationTime) {
        this.synchronizationTime = synchronizationTime;
    }

    public boolean isFromJira() {
        return isFromJira;
    }

    public void setFromJira(boolean fromJira) {
        isFromJira = fromJira;
    }

    public double getSpeakerCost() {
        return speakerCost;
    }

    public void setSpeakerCost(double speakerCost) {
        this.speakerCost = speakerCost;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Set<Participant> getSpeakers() {
        if (speakers == null) {
            speakers = new HashSet<>();
        }
        return speakers;
    }

    public void setSpeakers(Set<Participant> speakers) {
        this.speakers = speakers;
    }

    public Set<SpeechTag> getTags() {
        if (tags == null) {
            tags = new HashSet<>();
        }
        return tags;
    }

    public List<SpeechComment> getCommentList() {
        if (commentList == null) {
            commentList = new ArrayList<>();
        }
        return commentList;
    }

    public void setCommentList(List<SpeechComment> commentList) {
        this.commentList = commentList;
    }

    public void setTags(Set<SpeechTag> tags) {
        this.tags = tags;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public long getEventId() {
        return event.getId();
    }

    public String getEventName() {
        return event.getName() + " " + event.getVersion();
    }

    public List<Long> getParticipantsId() {
        return getSpeakers().stream().map(Participant :: getId).collect(Collectors.toList());
    }

    public Long getPartnerId() {
        if(partner != null) {
            return partner.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speech)) return false;
        if (!super.equals(o)) return false;

        Speech speech = (Speech) o;

        if (isFromJira != speech.isFromJira) return false;
        if (Double.compare(speech.speakerCost, speakerCost) != 0) return false;
        if (nameEN != null ? !nameEN.equals(speech.nameEN) : speech.nameEN != null) return false;
        if (fullDescription != null ? !fullDescription.equals(speech.fullDescription) : speech.fullDescription != null)
            return false;
        if (fullDescriptionEN != null ? !fullDescriptionEN.equals(speech.fullDescriptionEN) : speech.fullDescriptionEN != null)
            return false;
        if (viewerValue != null ? !viewerValue.equals(speech.viewerValue) : speech.viewerValue != null) return false;
        if (focus != null ? !focus.equals(speech.focus) : speech.focus != null) return false;
        if (shortDescription != null ? !shortDescription.equals(speech.shortDescription) : speech.shortDescription != null)
            return false;
        if (shortDescriptionEN != null ? !shortDescriptionEN.equals(speech.shortDescriptionEN) : speech.shortDescriptionEN != null)
            return false;
        if (jiraCreationTime != null ? !jiraCreationTime.equals(speech.jiraCreationTime) : speech.jiraCreationTime != null)
            return false;
        if (jiraUpdateTime != null ? !jiraUpdateTime.equals(speech.jiraUpdateTime) : speech.jiraUpdateTime != null)
            return false;
        if (jiraStatus != null ? !jiraStatus.equals(speech.jiraStatus) : speech.jiraStatus != null) return false;
        if (jiraLink != null ? !jiraLink.equals(speech.jiraLink) : speech.jiraLink != null) return false;
        if (synchronizationTime != null ? !synchronizationTime.equals(speech.synchronizationTime) : speech.synchronizationTime != null)
            return false;
        if(partner != null) {
            if (partner.getId().equals(speech.partner.getId()))
                return false;
        }
        if (event.getId() != null ? !event.getId().equals(speech.event.getId()) : speech.event.getId() != null) return false;
        /*
        if (!isEquals(this.speakers, speech.speakers)) {
            return false;
        }
        */
        long sumThis = 0;
        long sumThat = 0;
        for(Participant p : getSpeakers()) {
            sumThis += p.getId();
        }
        for(Participant p : speech.getSpeakers()) {
            sumThat += p.getId();
        }
        if(sumThis != sumThat) {
            return false;
        }

        if (this.commentList.size() != speech.commentList.size()) {
            return false;
        }
        return tags != null ? tags.equals(speech.tags) : speech.tags == null;
    }

    /*
        In hashcode() we have problem with comentList hashcode - he is not the same for the same objects.
        To fix this problem we use just list size.

     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (nameEN != null ? nameEN.hashCode() : 0);
        result = 31 * result + (fullDescription != null ? fullDescription.hashCode() : 0);
        result = 31 * result + (fullDescriptionEN != null ? fullDescriptionEN.hashCode() : 0);
        result = 31 * result + (viewerValue != null ? viewerValue.hashCode() : 0);
        result = 31 * result + (focus != null ? focus.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (shortDescriptionEN != null ? shortDescriptionEN.hashCode() : 0);
        result = 31 * result + (jiraCreationTime != null ? jiraCreationTime.hashCode() : 0);
        result = 31 * result + (jiraUpdateTime != null ? jiraUpdateTime.hashCode() : 0);
        result = 31 * result + (jiraStatus != null ? jiraStatus.hashCode() : 0);
        result = 31 * result + (jiraLink != null ? jiraLink.hashCode() : 0);
        result = 31 * result + (synchronizationTime != null ? synchronizationTime.hashCode() : 0);
        result = 31 * result + (isFromJira ? 1 : 0);
        temp = Double.doubleToLongBits(speakerCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        if(partner != null) {
            result = 31 * result + (partner.getId() != null ? partner.getId().hashCode() : 0);
        }
        if(partner != null) {
            result = 31 * result + (event.getId() != null ? event.getId().hashCode() : 0);
        }
        result = 31 * result + (speakers != null ? speakers.size() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (commentList != null ? commentList.size() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder speakerSB = new StringBuilder();
        if(!this.getSpeakers().isEmpty()){
            String prefix = "";
            speakerSB.append('[');
            for (Participant p : speakers) {
                speakerSB.append(prefix);
                prefix = ",";
                speakerSB.append((p.getFullName()));
            }
            speakerSB.append(']');
        }
        String partnerName = "";
        if(partner != null) {
            partnerName =  ", partner=" + partner.getName();
        }
        return "Speech{" +
                super.toString() +
                ", speaker=" + speakerSB.toString() +
                ", event=" + event.getName() +
                partnerName +
                "}";
    }
}
