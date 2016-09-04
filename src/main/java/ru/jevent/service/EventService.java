package ru.jevent.service;

import ru.jevent.model.Event;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

public interface EventService {

    Event save(Event event);

    void update(Event event) throws NotFoundException;

    Event get(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    List<Event> getAll();

}
