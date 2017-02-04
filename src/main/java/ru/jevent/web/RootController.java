package ru.jevent.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RootController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() {
        return new ModelAndView("redirect:" + profile());
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model,
                        @RequestParam(value = "error", required = false) boolean error,
                        @RequestParam(value = "message", required = false) String message) {

        model.put("error", error);
        model.put("message", message);
        return "login";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList() {return "userList"; }

    @RequestMapping(value = "/participants", method = RequestMethod.GET)
    public String participantList() {
        return "participantList";
    }

    @RequestMapping(value = "/speaker/{speakerId}", method = RequestMethod.GET)
    public String speaker(@PathVariable String speakerId, Model model) {
        model.addAttribute("speakerId", speakerId);
        return "speaker";
    }

    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.GET)
    public String event(@PathVariable String eventId, Model model) {
        model.addAttribute("eventId", eventId);
        return "event";
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

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        return "profile";
    }
}
