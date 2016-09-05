package ru.jevent.web.User;

import ru.jevent.LoggedUser;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.User;
import ru.jevent.service.UserService;

import java.util.List;

public class UserRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(UserRestController.class);

    private UserService service;

    public User get(long id) {
        long userId = LoggedUser.id();
        LOG.info("get user {} by user {}", id, userId);
        return service.get(id);
    }

    public List<User> getAll() {
        long userId = LoggedUser.id();
        LOG.info("get all users  by user {}", userId);
        return service.getAll();
    }
}
