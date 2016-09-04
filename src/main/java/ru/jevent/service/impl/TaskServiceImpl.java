package ru.jevent.service.impl;

import ru.jevent.model.Task;
import ru.jevent.repository.TaskRepository;
import ru.jevent.service.TaskService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TaskServiceImpl implements TaskService {

    private TaskRepository repository;

    @Override
    public Task save(Task task, long userId) {
        return repository.save(task, userId);
    }

    @Override
    public void update(Task task, long userId) throws NotFoundException {
        ExceptionUtil.check(repository.save(task, userId), task.getId());
    }

    @Override
    public Task getCreated(long id, long userId) throws NotFoundException {
        return ExceptionUtil.check(repository.getCreated(id, userId), id);
    }

    @Override
    public Task getAssigned(long id, long userId) throws NotFoundException {
        return ExceptionUtil.check(repository.getAssigned(id, userId), id);
    }

    @Override
    public void delete(long id, long userId) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id, userId), id);
    }

    @Override
    public List<Task> getAll(long userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<Task> getByInterval(LocalDateTime start, LocalDateTime end, long userId) {
        return repository.getByInterval(start, end.plus(1, ChronoUnit.DAYS), userId);
    }
}
