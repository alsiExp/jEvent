package ru.jevent.repository;

import ru.jevent.model.Participant;

import java.util.List;

public interface ParticipantRepository {

    Participant save(Participant participant);

    boolean delete(long id);

    Participant get(long id);

    Participant getByEmail(String email);

    List<Participant> getAll();
}
