package ru.jevent.web.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.jevent.model.Event;

import java.util.List;

@RestController
@RequestMapping("/rest/events")
public class EventRestController {

    private EventHelper helper;

    @Autowired
    public EventRestController(EventHelper helper) {
        this.helper = helper;
    }

    public Event create(Event event) {
        return helper.create(event);
    }

    public void update(Event event) {
        helper.update(event);
    }

    public void delete(long id) {
        helper.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Event get(@PathVariable("id") long id) {
        return helper.get(id);
    }
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getAll() {
        return helper.getAll();
    }
}
