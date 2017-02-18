package ru.jevent.web.Speech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Participant;
import ru.jevent.model.Speech;
import ru.jevent.model.additionalEntity.SpeechTag;
import ru.jevent.service.EventService;
import ru.jevent.service.ParticipantService;
import ru.jevent.service.SpeechService;
import ru.jevent.service.SpeechTagService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SpeechHelper {
    private static final LoggerWrapper LOG = LoggerWrapper.get(SpeechHelper.class);
    private final SpeechService service;
    private final SpeechTagService tagService;
    private final ParticipantService participantService;
    private final EventService eventService;

    @Autowired
    public SpeechHelper(SpeechService service, SpeechTagService tagService, ParticipantService participantService, EventService eventService) {
        this.service = service;
        this.tagService = tagService;
        this.participantService = participantService;
        this.eventService = eventService;
    }

    public Speech create (Speech speech) {
        LOG.info("create speech" + speech);
        return service.save(speech);
    }

    public Speech create (Speech speech, long eventId, long[] participantIds) {
        LOG.info("create speech" + speech + " for event id = " + eventId);
        for(long pId : participantIds) {
            speech.addSpeaker(participantService.get(pId));
        }
        speech.setEvent(eventService.get(eventId));
        return service.save(speech);
    }

    public void update(Speech newSpeech) {
        LOG.info("update " + newSpeech);
        Speech oldSpeech = service.get(newSpeech.getId());
        oldSpeech.updateFields(newSpeech);
        service.update(newSpeech);
    }

    public void update(Speech speech, long[] participantIds) {
        LOG.info("update " + speech);

        Set<Long> newSpeakers = new HashSet<>();
        for(long newId : participantIds){
            newSpeakers.add(newId);
        }
        Set<Long> oldSpeakers = new HashSet<>();
        for(Participant p : speech.getSpeakers()){
            oldSpeakers.add(p.getId());
        }
        for(long oldId : oldSpeakers) {
            if(!newSpeakers.contains(oldId)) {
                speech.removeSpeaker(participantService.get(oldId));
            }
        }

        for(long pId : participantIds) {
            if(!oldSpeakers.contains(pId)) {
                speech.addSpeaker(participantService.get(pId));
            }
        }
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

    public List<SpeechTag> getAllTags () {
        LOG.info("get all tags");
        return tagService.getAll();
    }

    public SpeechTag create(SpeechTag tag){
        LOG.info("create " + tag);
        return tagService.save(tag);
    }
}
