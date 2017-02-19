package ru.jevent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jevent.model.Speech;
import ru.jevent.model.additionalEntity.SpeechTag;
import ru.jevent.repository.SpeechRepository;
import ru.jevent.service.EventService;
import ru.jevent.service.ParticipantService;
import ru.jevent.service.SpeechService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

@Service
public class SpeechServiceImpl implements SpeechService{

    private SpeechRepository repository;
    private EventService eventService;
    private ParticipantService participantService;

    @Autowired
    public SpeechServiceImpl(SpeechRepository repository, EventService eventService, ParticipantService participantService) {
        this.repository = repository;
        this.eventService = eventService;
        this.participantService = participantService;
    }

    @Override
    public Speech save(Speech speech) {
        eventService.dropCache();
        participantService.dropCache();
        return repository.save(speech);

    }

    @Override
    public void update(Speech speech) throws NotFoundException {
        eventService.dropCache();
        participantService.dropCache();
        ExceptionUtil.check(repository.save(speech), speech.getId());
    }

    @Override
    public Speech get(long id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id), id);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        eventService.dropCache();
        participantService.dropCache();
        ExceptionUtil.check(repository.delete(id), id);
    }

    @Override
    public List<Speech> getByPartner(long id) {
        return repository.getByPartner(id);
    }

    @Override
    public Speech getByJiraId(int jiraId) {
        return repository.getByJiraId(jiraId);
    }

    @Override
    public List<SpeechTag> getPossibleTags(long speechId) {
        return repository.getPossibleTags(speechId);
    }
}
