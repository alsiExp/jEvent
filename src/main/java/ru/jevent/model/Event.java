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
    private ArrayList<Comment> commentList;

    //    ticket prices
    private List<Rate> rates;
    //    tracks with slots (in list)
    private List<Track> tracks;

    public Event() {
    }

    public Event(long id, String name) {
        super(id, name);
    }

    public Event(long id, String name, User author, String tagName, String description) {
        super(id, name);
        this.author = author;
        this.tagName = tagName;
        this.description = description;
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

    public ArrayList<Comment> getCommentList() {
        if(commentList == null) {
            commentList = new ArrayList<>();
        }
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
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
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name=" + name +
                ", author=" + author +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}