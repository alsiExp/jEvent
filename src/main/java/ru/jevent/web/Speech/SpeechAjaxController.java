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

        Speech speech = helper.get(id);
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

        speech.setTags(tagSet);
        helper.update(speech);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
