package ru.jevent.web.Participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Participant;
import ru.jevent.model.additionalEntity.Email;
import ru.jevent.service.ParticipantService;

import java.util.List;
import java.util.Objects;

@Component
public class ParticipantHelper {
    private static final LoggerWrapper LOG = LoggerWrapper.get(ParticipantHelper.class);

    private ParticipantService service;

    @Autowired
    public ParticipantHelper(ParticipantService service) {
        this.service = service;
    }

    public Participant create(Participant participant) {
        LOG.info("create " + participant);
        return service.save(participant);
    }

    public void update(Participant newPart) {
        LOG.info("update " + newPart);
        Participant oldPart = service.get(newPart.getId());
        if(!oldPart.getGitHub().getAccount().equals(newPart.getGitHub().getAccount())){
            oldPart.setGitHub(newPart.getGitHub());
            //TODO: delete old github?
        }
        if(!oldPart.getTwitter().getAccount().equals(newPart.getTwitter().getAccount())) {
            oldPart.setTwitter(newPart.getTwitter());
            //TODO: delete old twitter?
        }
        oldPart.updateFields(newPart);
        //TODO implement emails delete
        for(Email email : newPart.getEmails()) {
            if(!email.isNew()) {
                for (Email oldEmail : oldPart.getEmails()) {
                    if (Objects.equals(email.getId(), oldEmail.getId())){
                        oldEmail.setEmail(email.getEmail());
                    }
                }
            } else {
                oldPart.addEmail(email);
            }
        }

        service.update(oldPart);
    }

    public Participant get(long id) {
        LOG.info("get participant " + id);
        return service.get(id);
    }

    public void delete(long id) {
        LOG.info("delete participant " + id);
        service.delete(id);
    }

    public List<Participant> getByTag(long tagId) {
        LOG.info("get participants by tag " + tagId);
        return service.getByTag(tagId);
    }

    public List<Participant> getAll() {
        LOG.info("get all participant");
        return service.getAll();
    }
}
