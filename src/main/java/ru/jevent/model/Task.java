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

    public Task(String name, User author, Set<User> target, LocalDateTime start, LocalDateTime deadline,
                String description, TaskStatus taskStatus, Set<Attachable> attachList) {
        super(name);
        this.author = author;
        this.target = target;
        this.start = start;
        this.deadline = deadline;
        this.description = description;
        this.statusLog.add(taskStatus);
        this.attachList = attachList;
    }

    public Task(long id, String name, User author, Set<User> target, LocalDateTime start, LocalDateTime deadline,
                String description, TaskStatus taskStatus, Set<Attachable> attachList) {
        super(id, name);
        this.author = author;
        this.target = target;
        this.start = start;
        this.deadline = deadline;
        this.description = description;
        this.statusLog.add(taskStatus);
        this.attachList = attachList;
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

    public void setStatusLog(List<TaskStatus> statusLog) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        if (!super.equals(o)) return false;

        Task task = (Task) o;

        if (author != null ? !author.equals(task.author) : task.author != null) return false;
        if (target != null ? !target.equals(task.target) : task.target != null) return false;
        if (start != null ? !start.equals(task.start) : task.start != null) return false;
        if (deadline != null ? !deadline.equals(task.deadline) : task.deadline != null) return false;
        if (description != null ? !description.equals(task.description) : task.description != null) return false;
        if (statusLog != null ? !statusLog.equals(task.statusLog) : task.statusLog != null) return false;
        return attachList != null ? attachList.equals(task.attachList) : task.attachList == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (statusLog != null ? statusLog.hashCode() : 0);
        result = 31 * result + (attachList != null ? attachList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String prefix = "";
        StringBuilder targetSB = new StringBuilder();
        targetSB.append('[');
        for(User u : target) {
            targetSB.append(prefix);
            prefix = ",";
            targetSB.append(u.toString());
        }
        targetSB.append(']');
        prefix = "";

        StringBuilder logSB = new StringBuilder();
        logSB.append('[');
        for(TaskStatus s : statusLog) {
            logSB.append(prefix);
            prefix = ",";
            logSB.append(s.toString());
        }
        logSB.append(']');
        prefix = "";

        StringBuilder attachSB = new StringBuilder();
        attachSB.append('[');
        for(Attachable a : attachList) {
            attachSB.append(prefix);
            prefix = ",";
            attachSB.append(a.getAttachName());
        }
        attachSB.append(']');

        return "Task{" +
                super.toString() +
                ", author=" + author.toString() +
                ", target=" + targetSB.toString() +
                ", start=" + start +
                ", deadline=" + deadline +
                ", description='" + description + '\'' +
                ", statusLog=" + logSB.toString() +
                ", attachList=" + attachSB.toString() +
                "} ";
    }
}
