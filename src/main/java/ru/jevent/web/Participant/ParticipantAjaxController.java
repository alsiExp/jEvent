package ru.jevent.web.Participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.jevent.model.Participant;
import ru.jevent.model.Speech;
import ru.jevent.model.additionalEntity.Email;
import ru.jevent.model.additionalEntity.GitHub;
import ru.jevent.model.additionalEntity.Twitter;
import ru.jevent.model.enums.Sex;
import ru.jevent.web.Speech.SpeechHelper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/ajax/participants")
public class ParticipantAjaxController {
    private final ParticipantHelper helper;
    private final SpeechHelper speechHelper;

    @Autowired
    public ParticipantAjaxController(ParticipantHelper helper,
                                     SpeechHelper speechHelper) {
        this.helper = helper;
        this.speechHelper = speechHelper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Participant get(@PathVariable("id") long id) {
        return helper.get(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Participant> getAll() {
        return helper.getAll();
    }

    @RequestMapping(value = "/tag/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Participant> getByTag(@PathVariable("id") long tagId) {
        return helper.getByTag(tagId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        helper.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void update(@RequestParam("participantId") long id,
                       @RequestParam("firstName") String firstName,
                       @RequestParam("lastName") String lastName,
                       @RequestParam("sex") Sex sex,
                       @RequestParam("birthday") LocalDateTime birthday,
                       @RequestParam("registered") LocalDateTime registered,
                       @RequestParam("phone") String phone,
                       @RequestParam("skype") String skype,
                       @RequestParam("email") Set<Email> email,
                       @RequestParam("github") GitHub github,
                       @RequestParam("twitter") Twitter twitter,
                       @RequestParam("city") String city,
                       @RequestParam("employer") String employer,
                       @RequestParam("bio") String biography,
                       @RequestParam("description") String description,
                       @RequestParam("travelHelp") String travelHelp) {
        Participant participant = new Participant();
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.setSex(sex);
        participant.setBirthDay(birthday);
        participant.setRegistered(registered);
        participant.setPhone(phone);
        participant.setSkype(skype);
        participant.setEmails(email);
        participant.setGitHub(github);
        participant.setTwitter(twitter);
        participant.setCity(city);
        participant.setEmployer(employer);
        participant.setBiography(biography);
        participant.setDescription(description);
        participant.setTravelHelp(travelHelp);
        if(id == 0) {
            helper.create(participant);
        } else {
            participant.setId(id);
            helper.update(participant);
        }

    }

    @RequestMapping(value = "/{speakerId}/speeches", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Speech> getSpeeches(@PathVariable("speakerId") long id) {
        return helper.get(id).getSpeechSet();
    }

    @RequestMapping(value = "/{speakerId}/speeches/{id}", method = RequestMethod.DELETE)
    public void deleteSpeech(@PathVariable("id") long id) {
        speechHelper.delete(id);
    }

}
