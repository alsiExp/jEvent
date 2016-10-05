package ru.jevent.repository.mock;

import org.springframework.stereotype.Repository;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Task;
import ru.jevent.model.User;
import ru.jevent.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MockTaskRepositoryImpl implements TaskRepository {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MockTaskRepositoryImpl.class);


    private Task task = new Task(1, "Сделать что-то", new User(), null, LocalDateTime.now(),
            LocalDateTime.now().plus(7, ChronoUnit.DAYS), "task description", true, null, null);

    @Override
    public Task save(Task task) {
        return this.task;
    }

    @Override
    public Task get(long id) {
        return task;
    }

    @Override
    public boolean delete(long id) {
        return id != 0;
    }

    @Override
    public List<Task> getAssignedByInterval(LocalDateTime start, LocalDateTime end, long userId) {
        return new ArrayList<Task>(){{add(task);}};
    }

    @Override
    public List<Task> getAllCreated(long userId) {
        return new ArrayList<Task>(){{add(task);}};
    }

    @Override
    public List<Task> getAllAssigned(long userId) {
        return new ArrayList<Task>(){{add(task);}};
    }
}
