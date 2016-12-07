package ru.jevent.model.superclasses;

import ru.jevent.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseComment extends BaseEntity {

    @Column(name = "content", nullable = false)
    protected String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    protected User author;

    @Column(name = "date", nullable = false)
    protected LocalDateTime date;

    public BaseComment() {
    }

    public BaseComment(String content, User author, LocalDateTime date) {
        this.content = content;
        this.author = author;
        this.date = date;
    }


    public BaseComment(long id, String content, User author, LocalDateTime date) {
        super(id);
        this.content = content;
        this.author = author;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BaseComment baseComment = (BaseComment) o;
        if (id != null ? !id.equals(baseComment.id) : baseComment.id != null) {
            return false;
        }
        if (content != null ? !content.equals(baseComment.content) : baseComment.content != null) {
            return false;
        }
        if (!this.getAuthor().getId().equals(baseComment.getAuthor().getId())) {
            return false;
        }
        if(!date.equals(baseComment.date)) {
            return false;
        }
        return true;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + author.getId().hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BaseComment{"
                + super.toString() +
                ", content='" + content + '\'' +
                ", author=" + this.getAuthor().getFullName() +
                ", date=" + date +
                "} ";
    }
}
