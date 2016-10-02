package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.jevent.model.Task;
import ru.jevent.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaTaskRepositoryImpl implements TaskRepository {

    @Override
    public Task save(Task task) {
        return null;
    }

    @Override
    public Task get(long id) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public List<Task> getAssignedByInterval(LocalDateTime start, LocalDateTime end, long userId) {
        return null;
    }

    @Override
    public List<Task> getAllCreated(long userId) {
        return null;
    }

    @Override
    public List<Task> getAllAssigned(long userId) {
        return null;
    }
}

