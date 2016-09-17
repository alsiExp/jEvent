package ru.jevent.repository.mock;

import org.springframework.stereotype.Repository;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Task;
import ru.jevent.model.TaskStatus;
import ru.jevent.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MockTaskRepositoryImpl implements TaskRepository {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MockTaskRepositoryImpl.class);

    private Task task = new Task(1, "Сделать что-то", MockUserRepositoryImpl.getUser(), null, LocalDateTime.now(),
            LocalDateTime.now().plus(7, ChronoUnit.DAYS), "Event description" , new TaskStatus(), null, null);

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
    public List<Task> getByInterval(LocalDateTime start, LocalDateTime end, long userId) {
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
