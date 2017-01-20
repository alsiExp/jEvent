package ru.jevent.model.additionalEntity;

import ru.jevent.model.superclasses.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "speech_tags")
@NamedQueries({
        @NamedQuery(name = SpeechTag.DELETE, query = "DELETE from SpeechTag t where t.id = :id"),
        @NamedQuery(name = SpeechTag.ALL_SORTED, query = "SELECT t FROM SpeechTag t ORDER BY t.id")
})
public class SpeechTag extends BaseEntity {

    public static final String DELETE = "SpeechTag.delete";
    public static final String ALL_SORTED = "SpeechTag.getAllSorted";

    @Column(name = "tag")
    private String tag;


    public SpeechTag() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpeechTag)) return false;
        if (!super.equals(o)) return false;

        SpeechTag speechTag = (SpeechTag) o;

        return tag != null ? tag.equals(speechTag.tag) : speechTag.tag == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SpeechTag{" +
                super.toString() +
                ", tag='" + tag + '\'' +
                "} ";
    }
}
