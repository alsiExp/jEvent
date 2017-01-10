package ru.jevent.web.Participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.jevent.model.Participant;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/participants")
public class ParticipantAjaxController {
    private final ParticipantHelper helper;

    @Autowired
    public ParticipantAjaxController(ParticipantHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Participant get(@PathVariable("id") long id) {
        return helper.get(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Participant> getAll() {
        return helper.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        helper.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void update(@RequestParam("participantId") long id,
                       @RequestParam("firstName") String firstName,
                       @RequestParam("lastName") String lastName,
                       @RequestParam("sex") String sex,
                       @RequestParam("birthday") LocalDateTime birthday,
                       @RequestParam("phone") String phone,
                       @RequestParam("skype") String skype,
                       @RequestParam("email") String emails,
                       @RequestParam("github") String github,
                       @RequestParam("twitter") String twitter,
                       @RequestParam("city") String city,
                       @RequestParam("employer") String employer,
                       @RequestParam("bio") String biography,
                       @RequestParam("description") String description,
                       @RequestParam("travelHelp") String travelHelp) {
        Participant participant = new Participant();
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.setSex(participant, sex);
        participant.setBirthDay(birthday);


    }
}
