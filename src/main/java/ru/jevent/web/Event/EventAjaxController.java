package ru.jevent.web.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.jevent.model.Event;
import ru.jevent.model.Speech;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/ajax/events")
public class EventAjaxController {

    private final EventHelper helper;

    @Autowired
    public EventAjaxController(EventHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Event get(@PathVariable("id") long id) {
        return helper.get(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getAll() {
        return helper.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        helper.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void update() {

    }

    @RequestMapping(value = "/jira", method = RequestMethod.GET)
    public List<String> getAllFromJira() {
        return helper.getAllFromJira();
    }

    @RequestMapping(value = "/{eventId}/speeches", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Speech> getSpeeches(@PathVariable("eventId") long id) {
        return helper.get(id).getSpeeches();
    }
}
