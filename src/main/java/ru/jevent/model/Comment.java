package ru.jevent.model;

import java.time.LocalDateTime;


public class Comment extends BaseEntity {
    // simple linear comments
    private String content;
    private User autor;
    private LocalDateTime date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
