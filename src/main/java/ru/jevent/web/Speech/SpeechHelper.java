package ru.jevent.web.Speech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Speech;
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

    Speech create (Speech speech) {
        LOG.info("create " + speech);
        return service.save(speech);
    }

    void update(Speech speech) {
        LOG.info("update " + speech);
        service.update(speech);
    }

    Speech get(long id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    void delete(long id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    List<Speech> getByPartner(long id) {
        LOG.info("get speeches by partner " + id);
        return service.getByPartner(id);
    }
}
