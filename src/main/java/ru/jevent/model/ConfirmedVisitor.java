package ru.jevent.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events_confirmed_visitors")
public class ConfirmedVisitor extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "comment")
    private String payComment;

    @Column(name = "buy_date")
    private LocalDateTime buyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_id")
    private Rate rate;

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
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

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ConfirmedVisitor that = (ConfirmedVisitor) o;

        if (visitor != null ? !visitor.equals(that.visitor) : that.visitor != null) return false;
        if (event != null ? !event.equals(that.event) : that.event != null) return false;
        if (payComment != null ? !payComment.equals(that.payComment) : that.payComment != null) return false;
        if (buyDate != null ? !buyDate.equals(that.buyDate) : that.buyDate != null) return false;
        return rate != null ? rate.equals(that.rate) : that.rate == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        if(event != null && event.getId() != null) {
            result = 31 * result + event.getId().hashCode();
        }
        result = 31 * result + (visitor != null ? visitor.hashCode() : 0);
        result = 31 * result + (payComment != null ? payComment.hashCode() : 0);
        result = 31 * result + (buyDate != null ? buyDate.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConfirmedVisitor{" +
                super.toString() +
                "visitor=" + visitor.getFirstName() + " " + visitor.getLastName() +
                ", payComment='" + payComment + '\'' +
                ", buyDate=" + buyDate +
                ", rate=" + rate.getCost() + " at " + event.getName() +
                "}";
    }
}
