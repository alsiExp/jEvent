package ru.jevent.web.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.jevent.service.EventService;

@Controller
public class EventController {
    @Autowired
    EventService service;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String eventList(Model model) {
        model.addAttribute("eventList", service.getAll());
        return "eventList";
    }
}
