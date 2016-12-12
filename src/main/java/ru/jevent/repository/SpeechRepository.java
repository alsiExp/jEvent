package ru.jevent.repository;

import ru.jevent.model.Speech;

import java.util.List;

public interface SpeechRepository {

    Speech save(Speech speech);

    boolean delete(long id);

    Speech get(long id);

//    List<Speech> getAll();

}
