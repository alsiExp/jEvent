package ru.jevent.web.Participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.jevent.LoggedUser;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Participant;
import ru.jevent.service.ParticipantService;

import java.util.List;

@Controller
public class ParticipantRestController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(ParticipantRestController.class);

    private ParticipantService service;

    @Autowired
    public ParticipantRestController(ParticipantService service) {
        this.service = service;
    }

    public Participant create(Participant participant) {
        long userId = LoggedUser.id();
        LOG.info("create {} by user {}", participant, userId);
        return service.save(participant);
    }

    public void update(Participant participant) {
        long userId = LoggedUser.id();
        LOG.info("update {} by user {}", participant, userId);
        service.update(participant);
    }

    public Participant get(long id) {
        long userId = LoggedUser.id();
        LOG.info("get participant {} by user {}", id, userId);
        return service.get(id);
    }

    public void delete(long id) {
        long userId = LoggedUser.id();
        LOG.info("delete participant {} by user {}", id, userId);
        service.delete(id);
    }

    public List<Participant> getAll() {
        long userId = LoggedUser.id();
        LOG.info("get all participant by user {}", userId);
        return service.getAll();
    }
}
