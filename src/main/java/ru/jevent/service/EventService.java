package ru.jevent.service;

import ru.jevent.model.Event.Event;

import java.util.List;

public interface EventService {

    Event save(Event event, long userId);

    void update(Event event, long userId);

    void delete(long id, long userId);

    List<Event> getAll(long userId);

}
