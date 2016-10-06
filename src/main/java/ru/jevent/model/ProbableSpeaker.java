package ru.jevent.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events_probable_speakers")
public class ProbableSpeaker extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visitor_id")
    Visitor speaker;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    Event event;
    @Column(name = "send_date")
    LocalDateTime sendDate;
    @Column(name = "speech_name")
    String speechName;
    @Column(name = "speech_description")
    String speechDescription;
    @Column(name = "wish_price")
    double wishPrice;

    public ProbableSpeaker() {
    }

    public Visitor getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Visitor speaker) {
        this.speaker = speaker;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public String getSpeechName() {
        return speechName;
    }

    public void setSpeechName(String speechName) {
        this.speechName = speechName;
    }

    public String getSpeechDescription() {
        return speechDescription;
    }

    public void setSpeechDescription(String speechDescription) {
        this.speechDescription = speechDescription;
    }

    public double getWishPrice() {
        return wishPrice;
    }

    public void setWishPrice(double wishPrice) {
        this.wishPrice = wishPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProbableSpeaker that = (ProbableSpeaker) o;

        if (Double.compare(that.wishPrice, wishPrice) != 0) return false;
        if (speaker != null ? !speaker.equals(that.speaker) : that.speaker != null) return false;
        if (event != null ? !event.equals(that.event) : that.event != null) return false;
        if (sendDate != null ? !sendDate.equals(that.sendDate) : that.sendDate != null) return false;
        if (speechName != null ? !speechName.equals(that.speechName) : that.speechName != null) return false;
        return speechDescription != null ? speechDescription.equals(that.speechDescription) : that.speechDescription == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (speaker != null ? speaker.hashCode() : 0);
        result = 31 * result + (event != null ? event.hashCode() : 0);
        result = 31 * result + (sendDate != null ? sendDate.hashCode() : 0);
        result = 31 * result + (speechName != null ? speechName.hashCode() : 0);
        result = 31 * result + (speechDescription != null ? speechDescription.hashCode() : 0);
        temp = Double.doubleToLongBits(wishPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ProbableSpeaker{" +
                super.toString() +
                "speaker=" + speaker.getFirstName() + " " + speaker.getLastName() +
                ", event=" + event.getName() +
                ", sendDate=" + sendDate +
                ", speechName='" + speechName + '\'' +
                ", speechDescription='" + speechDescription + '\'' +
                ", wishPrice=" + wishPrice +
                "}";
    }
}
