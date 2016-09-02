package ru.jevent.model;


import java.util.ArrayList;
import java.util.LinkedList;

public class Event extends NamedEntity  implements Attachable{

    private User autor;

    private String tagName;
    private String adress;
    private String description;
    private byte[] image;

    private LinkedList<Visitor> probableSpeakers;
    private ArrayList<Comment> commentList;             // notes for events

    private LinkedList<Rate> rates;
    private LinkedList<Track> tracks;



    @Override
    public String getAttachName() {
        return name;
    }

    @Override
    public String getAttachDescription() {
        return description;
    }

    @Override
    public byte[] getAttachImage() {
        return this.getImage();
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        if(image == null) {
            image = new byte[0];
        }
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public LinkedList<Visitor> getProbableSpeakers() {
        if(probableSpeakers == null){
            probableSpeakers = new LinkedList<>();
        }
        return probableSpeakers;
    }

    public void setProbableSpeakers(LinkedList<Visitor> probableSpeakers) {
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

    public LinkedList<Rate> getRates() {
        if(rates == null) {
            rates = new LinkedList<>();
        }
        return rates;
    }

    public void setRates(LinkedList<Rate> rates) {
        this.rates = rates;
    }

    public LinkedList<Track> getTracks() {
        if(tracks == null) {
            tracks = new LinkedList<>();
        }
        return tracks;
    }

    public void setTracks(LinkedList<Track> tracks) {
        this.tracks = tracks;
    }
}