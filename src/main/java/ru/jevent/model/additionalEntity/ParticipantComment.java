package ru.jevent.model.additionalEntity;


import ru.jevent.model.Participant;
import ru.jevent.model.superclasses.BaseComment;

import javax.persistence.*;

@Entity
@Table(name = "participants_comments")
public class ParticipantComment extends BaseComment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ParticipantComment that = (ParticipantComment) o;

        return participant != null ? participant.getId().equals(that.participant.getId()) : that.participant == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (participant != null ? participant.getId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParticipantComment{"
                + super.toString() +
                "participant= " + participant.getFullName() +
                "}";
    }
}
