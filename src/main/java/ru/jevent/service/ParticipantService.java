package ru.jevent.service;

import ru.jevent.model.Participant;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

public interface ParticipantService {

    Participant save(Participant participant);

    void update(Participant participant) throws NotFoundException;

    Participant get(long id) throws NotFoundException;

    Participant getByEmail(String email) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    List<Participant> getAll();
}
