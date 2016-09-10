package ru.jevent.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Task extends NamedEntity {

    private User author;
    private Set<User> target;

    private LocalDateTime start;
    private LocalDateTime deadline;

    private String description;

    //    actual status is lastest
    //    sort by creationTime in DB
    private List<TaskStatus> statusLog;

    //    can be Event, Partner or Visitor
    private Set<Attachable> attachList;

    public Task() {
    }

    public Task(long id, String name, LocalDateTime start, LocalDateTime deadline, User author,
                TaskStatus statusLog, Set<Attachable> attachList) {
        super(id, name);
        this.start = start;
        this.deadline = deadline;
        this.author = author;
        this.getStatusLog().add(statusLog);
        this.getAttachList().addAll(attachList);
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<User> getTarget() {
        if(target == null) {
            target = new HashSet<User>();
        }
        return target;
    }

    public void setTarget(Set<User> target) {
        this.target = target;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TaskStatus> getStatusLog() {
        if(statusLog == null) {
            statusLog = new ArrayList<>();
        }
        return statusLog;
    }

    public void setStatusLog(ArrayList<TaskStatus> statusLog) {
        this.statusLog = statusLog;
    }

    public Set<Attachable> getAttachList() {
        if(attachList == null) {
            attachList = new HashSet<>();
        }
        return attachList;
    }

    public void setAttachList(Set<Attachable> attachList) {
        this.attachList = attachList;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                "name=" + name +
                "author=" + author +
                ", deadline=" + deadline +
                '}';
    }
}
