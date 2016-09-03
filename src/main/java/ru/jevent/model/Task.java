package ru.jevent.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Task extends NamedEntity {

    private User autor;
    private Set<User> executors;
    private Set<User> target;

    private LocalDateTime start;
    private LocalDateTime deadline;
    private String shortDescription;
    private String fullDescription;

    private ArrayList<TaskStatus> statusLog;        // actual status is lastest
    private Set<Attachable> attachList;       // from UC-1-1, can be Event, Partner or Visitor

    public Task() {
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public Set<User> getExecutors() {
        if(executors == null) {
            executors = new HashSet<User>();
        }
        return executors;
    }

    public void setExecutors(Set<User> executors) {
        this.executors = executors;
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
}
