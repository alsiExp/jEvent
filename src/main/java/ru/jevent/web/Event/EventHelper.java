package ru.jevent.web.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Event;
import ru.jevent.service.EventService;

import java.util.List;

@Component
public class EventHelper {
    private static final LoggerWrapper LOG = LoggerWrapper.get(EventRestController.class);


    private EventService service;

    @Autowired
    public EventHelper(EventService service) {
        this.service = service;
    }

    public Event create(Event event) {
        LOG.info("create " + event);
        return service.save(event);
    }

    public void update(Event event) {
        LOG.info("update " + event);
        service.update(event);
    }

    public Event get(long id) {
        LOG.info("get event " + id);
        return service.get(id);
    }

    public void delete(long id) {
        LOG.info("delete event " + id);
        service.delete(id);
    }


    public List<Event> getAll() {
        LOG.info("getAll events");
        return service.getAll();
    }

    public void getAllFromJira() {

    }
}
