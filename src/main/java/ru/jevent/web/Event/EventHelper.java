package ru.jevent.web.Event;

import net.rcarz.jiraclient.JiraException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.LoggedUser;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Event;
import ru.jevent.service.EventService;
import ru.jevent.service.JiraService;

import java.util.List;

@Component
public class EventHelper {
    private static final LoggerWrapper LOG = LoggerWrapper.get(EventHelper.class);


    private final EventService service;
    private final JiraService jiraService;

    @Autowired
    public EventHelper(EventService service, JiraService jiraService) {
        this.service = service;
        this.jiraService = jiraService;
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

    public List<Event> getAllFromJira() {
        try {
            List<Event> eventList =  jiraService.getAllEvent(LoggedUser.id());
            for(Event event : eventList) {
                service.simpleSave(event);
            }
            return eventList;

        } catch (JiraException je) {
            LOG.error(je.getMessage());
            if (je.getCause() != null)
                LOG.error(je.getCause().getMessage());
        }
        return null;
    }
}
