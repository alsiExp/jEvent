package ru.jevent.web.Speech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jevent.model.Speech;
import ru.jevent.model.additionalEntity.SpeechTag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/ajax/speeches")
public class SpeechAjaxController {
    private final SpeechHelper helper;

    @Autowired
    public SpeechAjaxController(SpeechHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Speech get(@PathVariable("id") long id) {
        return helper.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        helper.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> update(Speech newSpeech,
                                         @RequestParam(name = "eventId", required = false) long eventId,
                                         @RequestParam(name = "partId", required = false) long[] partId) {

        if(newSpeech.getId() == 0) {
            if(eventId == 0) {
                return new ResponseEntity<>("Event Id can`t be 0", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            for(long i : partId) {
                if(i == 0) {
                    return new ResponseEntity<>("Speaker Id can`t be 0", HttpStatus.UNPROCESSABLE_ENTITY);
                }
            }
            newSpeech.setJiraStatus("Local");
            helper.create(newSpeech, eventId, partId);

        } else {
            helper.update(newSpeech);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public List<SpeechTag> getAllSpeechTags() {
        return helper.getAllTags();
    }

    @RequestMapping(value = "/{id}/tags", method = RequestMethod.GET)
    public List<SpeechTag> getPossibleSpeechTags(@PathVariable("id") long id) {
        return helper.getPossibleTags(id);
    }

    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    public ResponseEntity<List<String>> saveTags(@RequestParam("speechId") long id,
                                           @RequestParam(value = "tags", required = false) String[] tags) {
        List<String> response = new ArrayList<>();
        Set<SpeechTag> tagSet = new HashSet<>();
        if(tags != null) {
            for (String s : tags) {
                int index = s.indexOf('-');
                SpeechTag tag = new SpeechTag();
                Long tagId = Long.parseLong(s.substring(0, index));
                tag.setTag(s.substring(index + 1));
                if (tagId != 0) {
                    tag.setId(tagId);
                } else {
                    helper.create(tag);
                }
                response.add(tag.getTag());
                tagSet.add(tag);
            }
        }

        helper.updateTags(id, tagSet);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}/participants", method = RequestMethod.POST)
    public ResponseEntity<String> setParticipants(@PathVariable("id") long speechId,
                                                  @RequestParam(value = "speakers") long[] speakers) {

        if(speakers.length == 0) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Speech speech = helper.get(speechId);
        helper.update(speech, speakers);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
