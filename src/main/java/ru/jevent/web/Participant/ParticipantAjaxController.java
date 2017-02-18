package ru.jevent.web.Participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.jevent.model.Participant;
import ru.jevent.model.Speech;
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
    public ResponseEntity<String> update(Participant newParticipant) {
        if(newParticipant.getBirthDay().equals(LocalDateTime.MIN)){
            newParticipant.setBirthDay(null);
        }
        if(newParticipant.getRegistered().equals(LocalDateTime.MIN)){
            newParticipant.setRegistered(LocalDateTime.now());
        }
        if(!newParticipant.getTwitter().isValid()) {
            newParticipant.setTwitter(null);
        }
        if(!newParticipant.getGitHub().isValid()){
            newParticipant.setGitHub(null);
        }
        newParticipant.setEnabled(true);
        if(newParticipant.getId() == 0) {
            helper.create(newParticipant);
        } else {
            helper.update(newParticipant);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{speakerId}/speeches", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Speech> getSpeeches(@PathVariable("speakerId") long id) {
        return helper.get(id).getSpeechSet();
    }

    @RequestMapping(value = "/{speakerId}/speeches/{id}", method = RequestMethod.DELETE)
    public void deleteSpeech(@PathVariable("id") long id) {
        speechHelper.delete(id);
    }

    @RequestMapping(value = "/{speakerId}/speeches/{id}", method = RequestMethod.GET)
    public Speech getSpeech(@PathVariable("id") long id) {
        return speechHelper.get(id);
    }

}
