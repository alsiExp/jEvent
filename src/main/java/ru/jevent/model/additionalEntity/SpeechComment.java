package ru.jevent.model.additionalEntity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import ru.jevent.model.Speech;
import ru.jevent.model.superclasses.BaseComment;

import javax.persistence.*;

@Entity
@Table(name = "speeches_comments")
public class SpeechComment extends BaseComment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speech_id", nullable = false)
    @JsonBackReference
    private Speech speech;

    public Speech getSpeech() {
        return speech;
    }

    public void setSpeech(Speech speech) {
        this.speech = speech;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SpeechComment that = (SpeechComment) o;

        return speech != null ? speech.getId().equals(that.speech.getId()) : that.speech == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (speech != null ? speech.getId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SpeechComment{"
                + super.toString() +
                "speech=" + speech.getName() +
                "}";
    }
}
