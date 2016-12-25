package ru.jevent.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ru.jevent.model.additionalEntity.Rate;
import ru.jevent.model.superclasses.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visitors")
public class Visitor extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "participant_id")
    @JsonBackReference
    private Participant participant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private Event event;

    @Column(name = "pay_comment")
    private String payComment;

    @Column(name = "buy_date")
    private LocalDateTime buyDate;

    /*
        Cost with discount and etc.
        If null, actual is cost from rate.
     */
    @Column(name = "real_cost")
    private double realCost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rate_id")
    private Rate rate;

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getPayComment() {
        return payComment;
    }

    public void setPayComment(String payComment) {
        this.payComment = payComment;
    }

    public LocalDateTime getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDateTime buyDate) {
        this.buyDate = buyDate;
    }

    public double getRealCost() {
        return realCost;
    }

    public void setRealCost(double realCost) {
        this.realCost = realCost;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visitor)) return false;
        if (!super.equals(o)) return false;

        Visitor visitor = (Visitor) o;

        if (Double.compare(visitor.realCost, realCost) != 0) return false;
        if (participant != null ? !participant.equals(visitor.participant) : visitor.participant != null) return false;
        if (event != null ? !event.getId().equals(visitor.event.getId()) : visitor.event != null) return false;
        if (payComment != null ? !payComment.equals(visitor.payComment) : visitor.payComment != null) return false;
        if (buyDate != null ? !buyDate.equals(visitor.buyDate) : visitor.buyDate != null) return false;
        return rate != null ? rate.equals(visitor.rate) : visitor.rate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (participant != null ? participant.hashCode() : 0);
        if(event != null) {
            result = 31 * result + (event.getId() != null ? event.getId().hashCode() : 0);
        }
        result = 31 * result + (payComment != null ? payComment.hashCode() : 0);
        result = 31 * result + (buyDate != null ? buyDate.hashCode() : 0);
        temp = Double.doubleToLongBits(realCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Participant{" +
                super.toString() +
                "participant=" + participant.getFullName() +
                ", payComment='" + payComment + '\'' +
                ", buyDate=" + buyDate +
                ", rate=" + rate.getCost() + " at " + event.getName() +
                "}";
    }
}
