package ru.jevent.web.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.jevent.LoggedUser;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Event;
import ru.jevent.service.EventService;

import java.util.List;

@Controller
public class EventRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(EventRestController.class);


    private EventService service;

    @Autowired
    public EventRestController(EventService service) {
        this.service = service;
    }

    public Event create(Event event) {
        long userId = LoggedUser.id();
        LOG.info("create {} by user {}", event, userId);
        return service.save(event);
    }

    public void update(Event event) {
        long userId = LoggedUser.id();
        LOG.info("update {} by user {}", event, userId);
        service.update(event);
    }

    public Event get(long id) {
        long userId = LoggedUser.id();
        LOG.info("get event {} by user {}", id, userId);
        return service.get(id);
    }

    public void delete(long id) {
        long userId = LoggedUser.id();
        LOG.info("delete event {} by user {}", id, userId);
        service.delete(id);
    }


    public List<Event> getAll() {
        long userId = LoggedUser.id();
        LOG.info("getAll event by user {}", userId);
        return service.getAll();
    }






}
