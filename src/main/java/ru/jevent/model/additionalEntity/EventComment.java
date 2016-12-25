package ru.jevent.model.additionalEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ru.jevent.model.Event;
import ru.jevent.model.superclasses.BaseComment;

import javax.persistence.*;

@Entity
@Table(name = "events_comments")
public class EventComment extends BaseComment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @JsonBackReference
    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventComment)) return false;
        if (!super.equals(o)) return false;

        EventComment that = (EventComment) o;

        return event != null ? event.getId().equals(that.event.getId()) : that.event == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (event != null ? event.getId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventComment{"
                + super.toString() +
                "event=" + event.getName() +
                "}";
    }
}
