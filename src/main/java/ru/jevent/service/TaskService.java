package ru.jevent.service;

import ru.jevent.model.Task;
import ru.jevent.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    Task save(Task task);

    void update(Task task) throws NotFoundException;

    Task get(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    List<Task> getAssignedByInterval(LocalDateTime start, LocalDateTime end, long userId);

    List<Task> getAllCreated(long userId) throws NotFoundException;

    List<Task> getAllAssigned(long userId) throws NotFoundException;


}
