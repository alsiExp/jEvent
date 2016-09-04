package ru.jevent.repository;

import ru.jevent.model.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository {
    // add archiv

    Task save(Task task, long userId);


    boolean delete(long id, long userId);


    Task getCreated(long id, long userId);

    Task getAssigned(long id, long userId);


    List<Task> getAll(long userId);


    List<Task> getByInterval(LocalDateTime start, LocalDateTime end, long userId);
}
