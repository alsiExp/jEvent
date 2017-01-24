package ru.jevent.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ru.jevent.model.additionalEntity.Rate;
import ru.jevent.model.superclasses.NamedEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Entity
@Table(name = "events")
@NamedQueries({
        @NamedQuery(name = Event.DELETE, query = "DELETE FROM Event e WHERE e.id = :id"),
        @NamedQuery(name = Event.ALL_SORTED, query = "SELECT e FROM Event e ORDER BY e.id")
})
public class    Event extends NamedEntity {
    /*

     */

    public static final String DELETE = "Event.delete";
    public static final String ALL_SORTED = "Event.getAllSorted";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    /*
        version - important field from remote jira.
        If event is not synchronized can be null
     */
    @Column(name = "jira_name")
    private String jiraName;
    @Column(name = "jira_link")
    private String jiraLink;
    @Column(name = "version")
    private String version;
    @Column(name = "address")
    private String address;
    @Column(name = "description")
    private String description;
    @Column(name = "logo_url")
    private String logoURL;
    @Column(name = "start_date")
    private LocalDateTime startDate;


    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Speech> speeches;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Visitor> visitors;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<EventPartner> eventPartners;
    /*
        ticket prices
    */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id", nullable = false)
    @OrderBy("start, cost")
    @JsonIgnore
    private List<Rate> rates;



    public Event() {
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getJiraName() {
        return jiraName;
    }

    public void setJiraName(String jiraName) {
        this.jiraName = jiraName;
    }

    public String getJiraLink() {
        return jiraLink;
    }

    public void setJiraLink(String jiraLink) {
        this.jiraLink = jiraLink;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String tagName) {
        this.version = tagName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Set<Speech> getSpeeches() {
        if (speeches == null) {
            speeches = new HashSet<>();
        }
        return speeches;
    }

    public void setSpeeches(Set<Speech> speeches) {
        this.speeches = speeches;
    }

    public Set<Visitor> getVisitors() {
        if (visitors == null) {
            visitors = new HashSet<>();
        }
        return visitors;
    }

    public void setVisitors(Set<Visitor> visitors) {
        this.visitors = visitors;
    }

    public Set<EventPartner> getEventPartners() {
        if (eventPartners == null) {
            eventPartners = new HashSet<>();
        }
        return eventPartners;
    }

    public void setEventPartners(Set<EventPartner> partners) {
        this.eventPartners = partners;
    }


    public List<Rate> getRates() {
        if (rates == null) {
            rates = new LinkedList<>();
        }
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public Map<String, Long> getSpeechesCount() {
        return getSpeeches().stream().collect(groupingBy(Speech::getJiraStatus, counting()));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        if (!super.equals(o)) {
            return false;
        }

        Event event = (Event) o;

        if (author != null ? !author.equals(event.author) : event.author != null) {
            return false;
        }
        if (version != null ? !version.equals(event.version) : event.version != null) {
            return false;
        }
        if (jiraName != null ? !jiraName.equals(event.jiraName) : event.jiraName != null) {
            return false;
        }
        if (jiraLink != null ? !jiraLink.equals(event.jiraLink) : event.jiraLink != null) {
            return false;
        }
        if (address != null ? !address.equals(event.address) : event.address != null) {
            return false;
        }
        if (description != null ? !description.equals(event.description) : event.description != null) {
            return false;
        }
        if (logoURL != null ? !logoURL.equals(event.logoURL) : event.logoURL != null) {
            return false;
        }
        if (startDate != null ? !startDate.equals(event.startDate) : event.startDate != null) {
            return false;
        }
        if (!isEquals(this.getSpeeches(), event.getSpeeches())) {
            return false;
        }
        if (!isEquals(this.getVisitors(), event.getVisitors())) {
            return false;
        }
        if (!isEquals(this.getEventPartners(), event.getEventPartners())) {
            return false;
        }
        return isEquals(this.getRates(), event.getRates());
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (jiraName != null ? jiraName.hashCode() : 0);
        result = 31 * result + (jiraLink != null ? jiraLink.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (logoURL != null ? logoURL.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (speeches != null ? speeches.hashCode() : 0);
        result = 31 * result + (visitors != null ? visitors.hashCode() : 0);
        result = 31 * result + (eventPartners != null ? eventPartners.hashCode() : 0);
        result = 31 * result + (rates != null ? rates.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                super.toString() +
                ", version='" + version + '\'' +
                ", author=" + author +
                ", startDate=" + startDate +
                "} ";
    }

}