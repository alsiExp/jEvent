package ru.jevent.repository;

import ru.jevent.model.additionalEntity.SpeechTag;

import java.util.List;

public interface SpeechTagRepository {
    SpeechTag save(SpeechTag tag);

    boolean delete(long id);

    SpeechTag get(long id);

    List<SpeechTag> getAll();
}
