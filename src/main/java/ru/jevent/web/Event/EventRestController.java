package ru.jevent.web.Event;

import ru.jevent.LoggedUser;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Event.Event;
import ru.jevent.service.EventService;

import java.util.List;

public class EventRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(EventRestController.class);

    private EventService service;

    public List<Event> getAll() {
        long userId = LoggedUser.id();
        LOG.info("getAllEvent for User {}", userId);
        return service.getAll(userId);
    }

    public void update(Event event) {
        long userId = LoggedUser.id();
        LOG.info("update {} for User {}", event, userId);
        service.update(event, userId);
    }

    public Event create(Event event) {
        long userId = LoggedUser.id();
        LOG.info("create {} for User {}", event, userId);
        return service.save(event, userId);
    }

    public void delete(long id) {
        long userId = LoggedUser.id();
        LOG.info("delete Event {} for User {}", id, userId);
        service.delete(id, userId);
    }
}
