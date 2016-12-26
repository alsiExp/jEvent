package ru.jevent.web.Participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Participant;
import ru.jevent.service.ParticipantService;

import java.util.List;

@Component
public class ParticipantHelper {
    private static final LoggerWrapper LOG = LoggerWrapper.get(ParticipantRestController.class);

    private ParticipantService service;

    @Autowired
    public ParticipantHelper(ParticipantService service) {
        this.service = service;
    }

    public Participant create(Participant participant) {
        LOG.info("create " + participant);
        return service.save(participant);
    }

    public void update(Participant participant) {
        LOG.info("update " + participant);
        service.update(participant);
    }

    public Participant get(long id) {
        LOG.info("get participant " + id);
        return service.get(id);
    }

    public void delete(long id) {
        LOG.info("delete participant " + id);
        service.delete(id);
    }

    public List<Participant> getAll() {
        LOG.info("get all participant");
        return service.getAll();
    }
}
