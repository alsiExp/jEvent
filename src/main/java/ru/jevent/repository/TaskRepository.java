package ru.jevent.repository;

import ru.jevent.model.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository {
    // add archiv

    Task save(Task task, long userId);

    Task get(long id, long userId);

    boolean delete(long id, long userId);

    List<Task> getByInterval(LocalDateTime start, LocalDateTime end, long userId);

    List<Task> getAllCreated(long userId);

    List<Task> getAllAssigned(long userId);
}
