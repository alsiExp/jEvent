package ru.jevent.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ru.jevent.model.additionalEntity.SpeechTag;
import ru.jevent.model.superclasses.NamedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "speeches")
@NamedQueries({
        @NamedQuery(name = Speech.DELETE, query = "DELETE from Speech s where s.id = :id"),
        @NamedQuery(name = Speech.GET_BY_PARTNER, query = "SELECT s FROM Speech s  LEFT JOIN FETCH s.partner p where p.id = :id"),
        @NamedQuery(name = Speech.GET_POSSIBLE_TAGS, query = "SELECT t FROM SpeechTag t WHERE NOT EXISTS (SELECT s FROM Speech s WHERE s.id  = :id AND t MEMBER of s.tags)"),
        @NamedQuery(name = Speech.BY_JIRA_ID, query = "SELECT s FROM Speech s WHERE s.jiraId = :jiraId")
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
    */

    public static final String DELETE = "Speech.delete";
    public static final String GET_BY_PARTNER = "Speech.byPartner";
    public static final String GET_POSSIBLE_TAGS = "Tags.bySpeech";
    public static final String BY_JIRA_ID = "Speech.getByJiraId";


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
    @Column(name = "plan")
    private String plan;

    @Column(name = "jira_id")
    private int jiraId;
    @Column(name = "jira_key")
    private String jiraKey;
    @Column(name = "jira_resolution")
    private String jiraResolution;
    @Column(name = "jira_comments")
    private String jiraComments;
    @Column(name = "jira_status")
    private String jiraStatus;
    @Column(name = "jira_link")
    private String jiraLink;

    @Column(name = "sync_time")
    private LocalDateTime jiraSync;
    @Column(name = "rating")
    private double rating;

    @Column(name = "speaker_cost")
    private double speakerCost;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "speeches_speech_tags",
            joinColumns = @JoinColumn(name = "speech_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id", unique = true))
    private Set<SpeechTag> tags;

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

    public int getJiraId() {
        return jiraId;
    }

    public void setJiraId(int jiraId) {
        this.jiraId = jiraId;
    }

    public String getJiraKey() {
        return jiraKey;
    }

    public void setJiraKey(String jiraKey) {
        this.jiraKey = jiraKey;
    }

    public String getJiraStatus() {
        return jiraStatus;
    }

    public void setJiraStatus(String jiraStatus) {
        this.jiraStatus = jiraStatus;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getJiraLink() {
        return jiraLink;
    }

    public void setJiraLink(String jiraLink) {
        this.jiraLink = jiraLink;
    }

    public String getJiraResolution() {
        return jiraResolution;
    }

    public void setJiraResolution(String jiraResolution) {
        this.jiraResolution = jiraResolution;
    }

    public String getJiraComments() {
        return jiraComments;
    }

    public void setJiraComments(String jiraComments) {
        this.jiraComments = jiraComments;
    }

    public LocalDateTime getJiraSync() {
        return jiraSync;
    }

    public void setJiraSync(LocalDateTime synchronizationTime) {
        this.jiraSync = synchronizationTime;
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

    public void addSpeaker(Participant p) {
        if(p != null) {
            p.addSpeech(this);
            getSpeakers().add(p);
        }
    }


    public void removeSpeaker(Participant p) {
        if(p != null) {
            p.removeSpeech(this);
            getSpeakers().remove(p);
        }
    }

    public Set<SpeechTag> getTags() {
        if (tags == null) {
            tags = new HashSet<>();
        }
        return tags;
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

    public void addTag(SpeechTag tag) {
        if(tag != null) {
            getTags().add(tag);
        }
    }

    public void addTag(Set<SpeechTag> tags) {
        if(tags != null) {
            getTags().addAll(tags);
        }
    }

    public long getEventId() {
        return event.getId();
    }

    public String getEventName() {
        return event.getName() + " " + event.getVersion();
    }

    public Map<Long, String> getParticipants() {
        return getSpeakers().stream().collect(Collectors.toMap(Participant :: getId, Participant :: getFullName));
    }

    public Long getPartnerId() {
        if(partner != null) {
            return partner.getId();
        } else {
            return null;
        }
    }

    public void updateFields(@NotNull Speech newSpeech) {
        setName(newSpeech.getName());
        setNameEN(newSpeech.getNameEN());
        setRating(newSpeech.getRating());
        setSpeakerCost(newSpeech.getSpeakerCost());
        setShortDescription(newSpeech.getShortDescription());
        setShortDescriptionEN(newSpeech.getShortDescriptionEN());
        setFullDescription(newSpeech.getFullDescription());
        setFullDescriptionEN(newSpeech.getFullDescriptionEN());
        setViewerValue(newSpeech.getViewerValue());
        setFocus(newSpeech.getFocus());
        setPlan(newSpeech.getPlan());
    }


    public boolean hasSpeaker(Participant participant) {
        for(Participant part : getSpeakers()) {
            if(Objects.equals(part.getId(), participant.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speech)) return false;
        if (!super.equals(o)) return false;

        Speech speech = (Speech) o;

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
        if (jiraId != speech.jiraId)
            return false;
        if (jiraKey != null ? !jiraKey.equals(speech.jiraKey) : speech.jiraKey != null)
            return false;
        if (plan != null ? !plan.equals(speech.plan) : speech.plan != null)
            return false;
        if (jiraResolution != null ? !jiraResolution.equals(speech.jiraResolution) : speech.jiraResolution != null)
            return false;
        if (jiraComments != null ? !jiraComments.equals(speech.jiraComments) : speech.jiraComments != null)
            return false;
        if (rating != speech.rating)
            return false;
        if (speakerCost != speech.speakerCost)
            return false;
        if (jiraStatus != null ? !jiraStatus.equals(speech.jiraStatus) : speech.jiraStatus != null) return false;
        if (jiraLink != null ? !jiraLink.equals(speech.jiraLink) : speech.jiraLink != null) return false;
        if (jiraSync != null ? !jiraSync.equals(speech.jiraSync) : speech.jiraSync != null)
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
        return tags != null ? tags.equals(speech.tags) : speech.tags == null;
    }

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
        result = 31 * result + jiraId;
        result = 31 * result + (jiraKey != null ? jiraKey.hashCode() : 0);
        result = 31 * result + (plan != null ? plan.hashCode() : 0);
        result = 31 * result + (jiraResolution != null ? jiraResolution.hashCode() : 0);
        result = 31 * result + (jiraComments != null ? jiraComments.hashCode() : 0);
        result = 31 * result + (jiraStatus != null ? jiraStatus.hashCode() : 0);
        result = 31 * result + (jiraLink != null ? jiraLink.hashCode() : 0);
        result = 31 * result + (jiraSync != null ? jiraSync.hashCode() : 0);
        temp = Double.doubleToLongBits(speakerCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
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
        return result;
    }

    @Override
    public String toString() {
        StringBuilder speakerSB = new StringBuilder();
        String eventName = "";
        String partnerName = "";
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
        if(event != null) {
            eventName = event.getName();
        }
        if(partner != null) {
            partnerName =  ", partner=" + partner.getName();
        }
        return "Speech{" +
                super.toString() +
                ", speaker=" + speakerSB.toString() +
                ", event=" + eventName +
                partnerName +
                "}";
    }
}
