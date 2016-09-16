package ru.jevent.model;

/*
    OfferDetails stored details of probable speaker (Visitor), that send offer to be speaker on Event.
    This is just datatype class, not Entity.
    Used only in Event, HashMap<Visitor, OfferDetails> probableSpeakers;
    sendDate - date and time of offer sent;
    speechName - ;
    speechDescription - ;
    wishPrice - quantity of money, that want speaker.
 */

import java.time.LocalDateTime;

public class OfferDetails {
    LocalDateTime sendDate;
    String speechName;
    String speechDescription;
    double wishPrice;

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

        OfferDetails that = (OfferDetails) o;

        if (Double.compare(that.wishPrice, wishPrice) != 0) return false;
        if (sendDate != null ? !sendDate.equals(that.sendDate) : that.sendDate != null) return false;
        if (speechName != null ? !speechName.equals(that.speechName) : that.speechName != null) return false;
        return speechDescription != null ? speechDescription.equals(that.speechDescription) : that.speechDescription == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = sendDate != null ? sendDate.hashCode() : 0;
        result = 31 * result + (speechName != null ? speechName.hashCode() : 0);
        result = 31 * result + (speechDescription != null ? speechDescription.hashCode() : 0);
        temp = Double.doubleToLongBits(wishPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "OfferDetails{" +
                "sendDate=" + sendDate +
                ", speechName='" + speechName + '\'' +
                ", speechDescription='" + speechDescription + '\'' +
                ", wishPrice=" + wishPrice +
                '}';
    }
}
