package ru.jevent.web.Speech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Speech;
import ru.jevent.model.additionalEntity.SpeechTag;
import ru.jevent.service.SpeechService;

import java.util.List;

@Component
public class SpeechHelper {
    private static final LoggerWrapper LOG = LoggerWrapper.get(Speech.class);
    private final SpeechService service;

    @Autowired
    public SpeechHelper(SpeechService service) {
        this.service = service;
    }

    public Speech create (Speech speech) {
        LOG.info("create " + speech);
        return service.save(speech);
    }

    public void update(Speech speech) {
        LOG.info("update " + speech);
        service.update(speech);
    }

    public Speech get(long id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    public void delete(long id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public List<Speech> getByPartner(long id) {
        LOG.info("get speeches by partner " + id);
        return service.getByPartner(id);
    }

    public List<SpeechTag> getPossibleTags (long speechId) {
        LOG.info("get possible tags for speech " + speechId);
        return service.getPossibleTags(speechId);
    }
}
