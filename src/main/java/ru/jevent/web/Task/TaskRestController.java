package ru.jevent.web.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.jevent.LoggedUser;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Task;
import ru.jevent.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TaskRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(TaskRestController.class);

    private TaskService service;

    @Autowired
    public TaskRestController(TaskService service) {
        this.service = service;
    }

    public Task create(Task task) {
        long userId = LoggedUser.id();
        LOG.info("create {} by user {}", task, userId);
        return service.save(task, userId);
    }

    public void update(Task task) {
        long userId = LoggedUser.id();
        LOG.info("update {} by user {}", task, userId);
        service.update(task, userId);
    }

    public Task get(long id) {
        long userId = LoggedUser.id();
        LOG.info("get task {} by user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(long id) {
        long userId = LoggedUser.id();
        LOG.info("delete task {} by user {}", id, userId);
        service.delete(id, userId);
    }

    public List<Task> getByInterval(LocalDateTime start, LocalDateTime end) {
        long userId = LoggedUser.id();
        LOG.info("get task by interval ({} - {}) by user {}", start, end, userId);
        return service.getByInterval(start, end, userId);
    }

    public List<Task> getAllCreated() {
        long userId = LoggedUser.id();
        LOG.info("get all task created by user {}", userId);
        return service.getAllCreated(userId);
    }

    public List<Task> getAllAssigned() {
        long userId = LoggedUser.id();
        LOG.info("get all task assigned by user {}", userId);
        return service.getAllAssigned(userId);
    }
}