package ru.jevent.web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.User;
import ru.jevent.service.UserService;

import java.util.List;

@Component
public class UserHelper {
    private static final LoggerWrapper LOG = LoggerWrapper.get(AdminRestController.class);
    @Autowired
    private UserService service;

    public User create(User user) {
        LOG.info("create " + user);
        return service.save(user);
    }

    public void update(User user) {
        LOG.info("update " + user);
        service.update(user);
    }

    public User get(long id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    public void delete(long id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public List<User> getAll() {
        LOG.info("get all");
        return service.getAll();
    }


}
