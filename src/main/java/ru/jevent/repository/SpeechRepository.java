package ru.jevent.repository;

import ru.jevent.model.Speech;

public interface SpeechRepository {

    Speech save(Speech speech);

    boolean delete(long id);

    Speech get(long id);

}
