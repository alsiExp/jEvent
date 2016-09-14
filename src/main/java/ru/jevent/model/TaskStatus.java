package ru.jevent.model;

import ru.jevent.model.Enums.CurrentTaskStatus;

import java.time.LocalDateTime;

public class TaskStatus extends BaseEntity{
    // TaskStatus stored status of task with creation time, description and creators name

    private String description;
    private User autor;
    private LocalDateTime creationTime;
    private CurrentTaskStatus status;

    public TaskStatus() {
    }

    public TaskStatus(String description, User autor, LocalDateTime creationTime, CurrentTaskStatus status) {
        this.description = description;
        this.autor = autor;
        this.creationTime = creationTime;
        this.status = status;
    }

    public TaskStatus(Long id, String description, User autor, LocalDateTime creationTime, CurrentTaskStatus status) {
        super(id);
        this.description = description;
        this.autor = autor;
        this.creationTime = creationTime;
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskStatus)) return false;
        if (!super.equals(o)) return false;

        TaskStatus that = (TaskStatus) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (autor != null ? !autor.equals(that.autor) : that.autor != null) return false;
        if (creationTime != null ? !creationTime.equals(that.creationTime) : that.creationTime != null) return false;
        return status == that.status;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (autor != null ? autor.hashCode() : 0);
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaskStatus{"
                + super.toString() +
                ", description='" + description + '\'' +
                ", autor=" + autor.toString() +
                ", creationTime=" + creationTime +
                ", status=" + status +
                "} " ;
    }
}
