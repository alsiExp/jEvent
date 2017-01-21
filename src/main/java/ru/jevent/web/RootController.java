package ru.jevent.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RootController {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList() {
        return "userList";
    }

    @RequestMapping(value = "/participants", method = RequestMethod.GET)
    public String participantList() {
        return "participantList";
    }

    @RequestMapping(value = "/speaker/{speakerId}", method = RequestMethod.GET)
    public String speaker(@PathVariable String speakerId, Model model) {
        model.addAttribute("speakerId", speakerId);
        return "speaker";
    }

    @RequestMapping(value = "/speech/{speechId}", method = RequestMethod.GET)
    public String speech(@PathVariable String speechId, Model model) {
        model.addAttribute("speechId", speechId);
        return "speech";
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String eventList() {
        return "eventList";
    }
}
