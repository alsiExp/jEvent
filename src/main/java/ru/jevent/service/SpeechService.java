package ru.jevent.service;

import ru.jevent.model.Speech;
import ru.jevent.model.additionalEntity.SpeechTag;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

public interface SpeechService {

    Speech save(Speech speech);

    void update(Speech speech) throws NotFoundException;

    Speech get(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    List<Speech> getByPartner(long id);

    List<SpeechTag> getPossibleTags(long speechId);
}
