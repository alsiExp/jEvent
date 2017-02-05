package ru.jevent.web.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.jevent.model.Event;
import ru.jevent.model.Speech;

import java.util.List;
import java.util.Map;
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

    @RequestMapping(value = "/{eventId}/speeches", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Speech> getSpeeches(@PathVariable("eventId") long id) {
        return helper.get(id).getSpeeches();
    }


    @RequestMapping(value = "/jira", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<String>>>  getAllEventsFromJira() {
        Map<String, List<String>> response =  helper.getAllEventsFromJira();
        if(response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @RequestMapping(value = "/{eventId}/speeches/jira", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<String>>> getJiraSpeeches(@PathVariable("eventId") long id) {
        Map<String, List<String>> response = helper.getSpeechesByEvent(id);
        if(response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
