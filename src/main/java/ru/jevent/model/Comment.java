package ru.jevent.model;

import java.time.LocalDateTime;


public class Comment extends BaseEntity {
    // simple linear comments
    private String content;
    private User author;
    private LocalDateTime date;

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
        return "Comment{" +
                "id=" + id +
                ", author=" + author +
                ", date=" + date +
                '}';
    }
}
