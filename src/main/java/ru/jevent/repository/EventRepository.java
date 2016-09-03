package ru.jevent.repository;


import ru.jevent.model.Event;

import java.util.List;

public interface EventRepository {

    Event save(Event event);

    // false if not found
    boolean delete(long id);

    // null if not found
    Event get(long id);

    List<Event> getAll();
}
