package ru.jevent.model.Task;

import ru.jevent.model.NamedEntity;
import ru.jevent.model.Team.User;
import ru.jevent.service.Attachable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;


public class Task extends NamedEntity {


    private LocalDateTime startTime;
    private LocalDateTime deadlineTime;
    private String shortDescription;
    private String fullDescription;
    private ArrayList<User> target;
    private User autor;
    private Set<User> executors;
    private ArrayList<TaskStatus> statusLog;        // actual status is lastest
    private ArrayList<Attachable> attachList;       // from UC-1-1, can be Event, Partner or Visitor



    public enum CurrentTaskStatus {
        NEW, PAUSED, IN_WORK, DONE, FAILED
    }
}
