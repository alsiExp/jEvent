package ru.jevent.model.Task;

import ru.jevent.model.Team.User;

import java.time.LocalDateTime;

public class TaskStatus {
    // TaskStatus stored status of task with creation time, description and creators name
    private long id;
    private String description;
    private User autor;
    private LocalDateTime creationTime;
    private Task.CurrentTaskStatus status;
}
