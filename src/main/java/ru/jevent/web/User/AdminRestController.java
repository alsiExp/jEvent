package ru.jevent.web.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.User;
import ru.jevent.service.UserService;

import java.util.List;

@Service
public class AdminRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(AdminRestController.class);

    private UserService service;

    @Autowired
    public AdminRestController(UserService service) {
        this.service = service;
    }

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
