package ru.jevent.web.Speech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.jevent.model.Speech;
import ru.jevent.model.additionalEntity.SpeechTag;

import java.util.List;

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

    @RequestMapping(value = "/{id}/tags", method = RequestMethod.GET)
    public List<SpeechTag> getAllSpeechTags(@PathVariable("id") long id) {
        return helper.getPossibleTags(id);
    }

    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    public void saveTags(@RequestParam("speechId") long id,
                         @RequestParam("tags") String[] tags) {

        if(tags != null){
            Speech speech = helper.get(id);
/*            speech.addTag(tags);
            helper.update(speech);*/
        }
    }

}
