package ru.jevent.model;

import java.time.LocalDateTime;

/*
    PayDetails stored details of Visitors pay, when they buy ticket to Event.
    This is just datatype class, not Entity.
    Used only in Event, HashMap<Visitor, PayDetails> confirmedVisitors;
    date - date and time of buy.
    rate - rate, where stored cost end type of ticket.
 */
public class PayDetails {

    private LocalDateTime date;
    private Rate rate;

    public LocalDateTime getDate() {
        return date;
    }

    public PayDetails() {
    }

    public PayDetails(LocalDateTime date, Rate rate) {
        this.date = date;
        this.rate = rate;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

        PayDetails that = (PayDetails) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return rate != null ? rate.equals(that.rate) : that.rate == null;

    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayDetails{" +
                "date=" + date +
                ", rate=" + rate.toString() +
                '}';
    }
}
