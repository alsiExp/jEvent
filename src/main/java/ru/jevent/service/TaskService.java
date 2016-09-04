package ru.jevent.service;

import ru.jevent.model.Task;
import ru.jevent.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    Task save(Task task, long userId);

    void update(Task task, long userId) throws NotFoundException;

    Task getCreated(long id, long userId) throws NotFoundException;

    Task getAssigned(long id, long userId) throws NotFoundException;

    void delete(long id, long userId) throws NotFoundException;

    List<Task> getAll(long userId);

    List<Task> getByInterval(LocalDateTime start, LocalDateTime end, long userId);
}
