package ru.jevent.service;

import ru.jevent.model.additionalEntity.SpeechTag;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

public interface SpeechTagService {

    SpeechTag save(SpeechTag tag);

    void update (SpeechTag tag) throws NotFoundException;

    SpeechTag get (long id) throws NotFoundException;

    void delete (long id) throws NotFoundException;

    List<SpeechTag> getAll();
}
