package ru.jevent.model;

import ru.jevent.model.Enums.CurrentTaskStatus;
import ru.jevent.model.Task;
import ru.jevent.model.User;

import java.time.LocalDateTime;

public class TaskStatus extends BaseEntity{
    // TaskStatus stored status of task with creation time, description and creators name

    private String description;
    private User autor;
    private LocalDateTime creationTime;
    private CurrentTaskStatus status;

    public TaskStatus() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public CurrentTaskStatus getStatus() {
        return status;
    }

    public void setStatus(CurrentTaskStatus status) {
        this.status = status;
    }
}
