package ru.jevent.model;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "events")
@NamedQueries({
        @NamedQuery(name = Event.DELETE, query = "DELETE FROM Event e WHERE e.id = :id"),
        @NamedQuery(name = Event.ALL_SORTED, query = "SELECT e FROM Event e ORDER BY e.id")
})
public class Event extends NamedEntity {
    /*
        Approved speakers stored in they Slots (in Tracks).
     */

    public static final String DELETE = "Event.delete";
    public static final String ALL_SORTED = "Event.getAllSorted";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "tag_name")
    private String tagName;
    @Column(name = "address")
    private String address;
    @Column(name ="description")
    private String description;
    @Column(name = "logo_url")
    private String logoURL;
    @Column(name = "start")
    private LocalDateTime startDate;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProbableSpeaker> probableSpeakers;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ConfirmedVisitor> confirmedVisitors;

    //    ticket prices
    //    sort by LocalDate start
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id", nullable = false)
    @OrderBy("start, cost")
    private List<Rate> rates;

    //    tracks with slots
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id", nullable = false)
    private Set<Track> tracks;

    //    notes for Event
    //    sort by date
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "events_comments",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id", unique = true))
    @OrderBy("date")
    private List<Comment> commentList;


    public Event() {
    }

    public Event(long id, String name, User author, String tagName, String address, String description, String logoURL,
                 LocalDateTime startDate, Set<ProbableSpeaker> probableSpeakers, Set<ConfirmedVisitor> confirmedVisitors, List<Rate> rates, Set<Track> tracks, List<Comment> commentList) {
        super(id, name);
        this.author = author;
        this.tagName = tagName;
        this.address = address;
        this.description = description;
        this.logoURL = logoURL;
        this.startDate = startDate;
        this.probableSpeakers = probableSpeakers;
        this.confirmedVisitors = confirmedVisitors;
        this.rates = rates;
        this.tracks = tracks;
        this.commentList = commentList;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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

    public Set<ProbableSpeaker> getProbableSpeakers() {
        if (probableSpeakers == null) {
            probableSpeakers = new HashSet<>();
        }

        return probableSpeakers;
    }

    public void setProbableSpeakers(Set<ProbableSpeaker> probableSpeakers) {
        this.probableSpeakers = probableSpeakers;
    }

    public Set<ConfirmedVisitor> getConfirmedVisitors() {
        if (confirmedVisitors == null) {
            confirmedVisitors = new HashSet<>();
        }
        return confirmedVisitors;
    }

    public void setConfirmedVisitors(Set<ConfirmedVisitor> confirmedVisitors) {
        this.confirmedVisitors = confirmedVisitors;
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

    public List<Rate> getRates() {
        if (rates == null) {
            rates = new LinkedList<>();
        }
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public Set<Track> getTracks() {
        if (tracks == null) {
            tracks = new HashSet<>();
        }
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
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
        if (tagName != null ? !tagName.equals(event.tagName) : event.tagName != null) {
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
        if(!isEquals(this.getProbableSpeakers(), event.getProbableSpeakers())) {
            return  false;
        }
        if(!isEquals(this.getConfirmedVisitors(), event.getConfirmedVisitors())) {
            return  false;
        }
        if(!isEquals(this.getCommentList(), event.getCommentList())) {
            return false;
        }
        if (!isEquals(this.getRates(), event.getRates())) {
            return false;
        }

        if (!isEquals(this.getTracks(), event.getTracks())) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (logoURL != null ? logoURL.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (probableSpeakers != null ? probableSpeakers.hashCode() : 0);
        result = 31 * result + (confirmedVisitors != null ? confirmedVisitors.hashCode() : 0);
        result = 31 * result + (commentList != null ? commentList.hashCode() : 0);
        result = 31 * result + (rates != null ? rates.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                super.toString() +
                ", author=" + author +
                ", tagName='" + tagName + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", logoURL='" + logoURL + '\'' +
                ", startDate=" + startDate +
                ", probableSpeakers=" + probableSpeakers.toString() +
                ", confirmedVisitors=" + confirmedVisitors +
                ", rates=" + rates +
                ", tracks=" + tracks +
                ", commentList=" + commentList +
                "} ";
    }

}