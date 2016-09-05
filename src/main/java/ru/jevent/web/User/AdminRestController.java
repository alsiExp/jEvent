package ru.jevent.web.User;

import ru.jevent.LoggerWrapper;
import ru.jevent.model.User;
import ru.jevent.service.UserService;
import ru.jevent.web.Event.EventRestController;

import java.util.List;

public class AdminRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(EventRestController.class);

    private UserService service;

    public User create(User user) {
        LOG.info("create user {} by admin", user);
        return service.save(user);
    }

    public void update(User user) {
        LOG.info("update user {} by admin", user);
        service.update(user);
    }

    public User get(long id) {
        LOG.info("get user {} by admin", id);
        return service.get(id);
    }

    public void delete(long id) {
        LOG.info("delete user {} by admin", id);
        service.delete(id);
    }

    public List<User> getAll() {
        LOG.info("get all users  by admin");
        return service.getAll();
    }
}
