package ru.jevent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jevent.model.Task;
import ru.jevent.repository.TaskRepository;
import ru.jevent.service.TaskService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository repository;

    @Autowired
    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task save(Task task) {
        return repository.save(task);
    }

    @Override
    public void update(Task task) throws NotFoundException {
        ExceptionUtil.check(repository.save(task), task.getId());
    }

    @Override
    public Task get(long id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id), id);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id), id);
    }


    @Override
    public List<Task> getByInterval(LocalDateTime start, LocalDateTime end, long userId) {
        return repository.getByInterval(start, end.plus(1, ChronoUnit.DAYS), userId);
    }

    @Override
    public List<Task> getAllCreated(long userId) throws NotFoundException {
        return repository.getAllCreated(userId);
    }

    @Override
    public List<Task> getAllAssigned(long userId) throws NotFoundException {
        return repository.getAllAssigned(userId);
    }
}
