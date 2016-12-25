package ru.jevent.web.Speech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.jevent.model.Speech;

@RestController
@RequestMapping("/rest/speeches")
public class SpeechRestController {
    private SpeechHelper helper;

    @Autowired
    public SpeechRestController(SpeechHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Speech get(@PathVariable("id") long id) {
        return helper.get(id);
    }
}
