package ru.jevent.model.additionalEntity;


import ru.jevent.model.superclasses.BaseComment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "speeches_comments")
public class SpeechComment extends BaseComment {

    @Column(name="speech_id")
    private long speechId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SpeechComment that = (SpeechComment) o;

        return speechId == that.speechId;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (speechId ^ (speechId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "SpeechComment{"
                + super.toString() +
                "speechId=" + speechId +
                "}";
    }
}
