package ru.jevent.model;

import java.time.LocalDateTime;


public class Comment extends BaseEntity {
    // simple linear comments
    private String content;
    private User author;
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
                ", author=" + author +
                ", date=" + date +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        if (content != null ? !content.equals(comment.content) : comment.content != null) return false;
        if (!author.equals(comment.author)) return false;
        return date.equals(comment.date);

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
