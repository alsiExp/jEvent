package ru.jevent.model.additionalEntity;


import ru.jevent.model.superclasses.BaseComment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "participants_comments")
public class ParticipantComment extends BaseComment {

    @Column(name="participant_id")
    private long participantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ParticipantComment that = (ParticipantComment) o;

        return participantId == that.participantId;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (participantId ^ (participantId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "SpeechComment{"
                + super.toString() +
                "participantId=" + participantId +
                "}";
    }
}
