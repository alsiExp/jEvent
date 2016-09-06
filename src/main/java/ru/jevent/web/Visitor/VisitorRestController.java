package ru.jevent.web.Visitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.jevent.LoggedUser;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Visitor;
import ru.jevent.service.VisitorService;

import java.util.List;

@Controller
public class VisitorRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(VisitorRestController.class);

    private VisitorService service;

    @Autowired
    public VisitorRestController(VisitorService service) {
        this.service = service;
    }

    public Visitor create(Visitor visitor) {
        long userId = LoggedUser.id();
        LOG.info("create {} by user {}", visitor, userId);
        return service.save(visitor);
    }

    public void update(Visitor visitor) {
        long userId = LoggedUser.id();
        LOG.info("update {} by user {}", visitor, userId);
        service.update(visitor);
    }

    public Visitor get(long id) {
        long userId = LoggedUser.id();
        LOG.info("get visitor {} by user {}", id, userId);
        return service.get(id);
    }

    public void delete(long id) {
        long userId = LoggedUser.id();
        LOG.info("delete visitor {} by user {}", id, userId);
        service.delete(id);
    }

    public List<Visitor> getAll() {
        long userId = LoggedUser.id();
        LOG.info("get all visitor by user {}", userId);
        return service.getAll();
    }
}
