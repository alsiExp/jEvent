package ru.jevent.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Task extends NamedEntity {

    private User author;
    private Set<User> target;

    private LocalDateTime start;
    private LocalDateTime deadline;
    private String shortDescription;
    private String fullDescription;

    private ArrayList<TaskStatus> statusLog;        // actual status is lastest
    private Set<Attachable> attachList;       // from UC-1-1, can be Event, Partner or Visitor

    public Task() {
    }

    public Task(long id, String name, LocalDateTime start, LocalDateTime deadline, User author, String shortDescription,
                TaskStatus statusLog, Set<Attachable> attachList) {
        super(id, name);
        this.start = start;
        this.deadline = deadline;
        this.author = author;
        this.shortDescription = shortDescription;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public ArrayList<TaskStatus> getStatusLog() {
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
