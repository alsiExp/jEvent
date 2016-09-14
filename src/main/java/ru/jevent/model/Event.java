package ru.jevent.model;


import java.util.*;


public class Event extends NamedEntity  implements Attachable{

    private User author;

    private String tagName;
    private String address;
    private String description;
    private String logoURL;

    private Set<Visitor> probableSpeakers;
    //    notes for Event
    //    sort by date
    private List<Comment> commentList;

    //    ticket prices
    //    sort by LocalDate start
    private List<Rate> rates;
    //    tracks with slots (in list)
    //    sort by field position in DB
    private List<Track> tracks;

    public Event() {
    }

    public Event(String name, User author, String tagName, String address, String description, String logoURL,
                 Set<Visitor> probableSpeakers, List<Comment> commentList, List<Rate> rates, List<Track> tracks) {
        super(name);
        this.author = author;
        this.tagName = tagName;
        this.address = address;
        this.description = description;
        this.logoURL = logoURL;
        this.probableSpeakers = probableSpeakers;
        this.commentList = commentList;
        this.rates = rates;
        this.tracks = tracks;
    }

    public Event(long id, String name, User author, String tagName, String address, String description, String logoURL,
                 Set<Visitor> probableSpeakers, List<Comment> commentList, List<Rate> rates, List<Track> tracks) {
        super(id, name);
        this.author = author;
        this.tagName = tagName;
        this.address = address;
        this.description = description;
        this.logoURL = logoURL;
        this.probableSpeakers = probableSpeakers;
        this.commentList = commentList;
        this.rates = rates;
        this.tracks = tracks;
    }

    @Override
    public String getAttachName() {
        return name;
    }

    @Override
    public String getAttachDescription() {
        return description;
    }

    @Override
    public String getAttachImageURL() {
        return this.getLogoURL();
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

    public Set<Visitor> getProbableSpeakers() {
        if(probableSpeakers == null) {
           probableSpeakers = new HashSet<>();
        }

        return probableSpeakers;
    }

    public void setProbableSpeakers(Set<Visitor> probableSpeakers) {
        this.probableSpeakers = probableSpeakers;
    }

    public List<Comment> getCommentList() {
        if(commentList == null) {
            commentList = new ArrayList<>();
        }
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Rate> getRates() {
        if(rates == null) {
            rates = new LinkedList<>();
        }
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public List<Track> getTracks() {
        if(tracks == null) {
            tracks = new LinkedList<>();
        }
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        if (!super.equals(o)) return false;

        Event event = (Event) o;

        if (author != null ? !author.equals(event.author) : event.author != null) return false;
        if (tagName != null ? !tagName.equals(event.tagName) : event.tagName != null) return false;
        if (address != null ? !address.equals(event.address) : event.address != null) return false;
        if (description != null ? !description.equals(event.description) : event.description != null) return false;
        if (logoURL != null ? !logoURL.equals(event.logoURL) : event.logoURL != null) return false;
        if (probableSpeakers != null ? !probableSpeakers.equals(event.probableSpeakers) : event.probableSpeakers != null)
            return false;
        if (commentList != null ? !commentList.equals(event.commentList) : event.commentList != null) return false;
        if (rates != null ? !rates.equals(event.rates) : event.rates != null) return false;
        return tracks != null ? tracks.equals(event.tracks) : event.tracks == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (logoURL != null ? logoURL.hashCode() : 0);
        result = 31 * result + (probableSpeakers != null ? probableSpeakers.hashCode() : 0);
        result = 31 * result + (commentList != null ? commentList.hashCode() : 0);
        result = 31 * result + (rates != null ? rates.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                super.toString() +
                ", logoURL='" + logoURL + '\'' +
                ", authorId=" + author.toString() +
                ", tagName='" + tagName + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                "} ";
    }


}