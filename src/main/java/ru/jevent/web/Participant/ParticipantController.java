package ru.jevent.web.Participant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ParticipantController {

    @RequestMapping(value = "/participants", method = RequestMethod.GET)
    public String userList() {
        return "participantList";
    }
}
