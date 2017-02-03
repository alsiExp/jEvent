package ru.jevent.repository;

import ru.jevent.model.Speech;
import ru.jevent.model.additionalEntity.SpeechTag;

import java.util.List;

public interface SpeechRepository {

    Speech save(Speech speech);

    boolean delete(long id);

    Speech get(long id);

    List<Speech> getByPartner(long id);

    Speech getByJiraId(int jiraId);

    List<SpeechTag> getPossibleTags(long speechId);

}
