package ru.jevent.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@NamedQueries({

})
public class Comment extends BaseEntity {
    // simple linear comments
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public Comment() {
    }

    public Comment(String content, User author, LocalDateTime date) {
        this.content = content;
        this.author = author;
        this.date = date;
    }


    public Comment(long id, String content, User author, LocalDateTime date) {
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
    public String toString() {
        return "Comment{"
                + super.toString() +
                ", content='" + content + '\'' +
                ", author=" + author.toString() +
                ", date=" + date +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        if (content != null ? !content.equals(comment.content) : comment.content != null) {
            return false;
        }
        if (!author.equals(comment.author)) {
            return false;
        }
        if(!date.equals(comment.date)) {
            return false;
        }
        return true;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + author.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
