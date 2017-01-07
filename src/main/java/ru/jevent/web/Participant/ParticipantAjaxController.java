package ru.jevent.web.Participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.jevent.model.Participant;

import java.util.List;

@RestController
@RequestMapping("/ajax/participants")
public class ParticipantAjaxController {
    private final ParticipantHelper helper;

    @Autowired
    public ParticipantAjaxController(ParticipantHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Participant> getAll() {
        return helper.getAll();
    }
}
