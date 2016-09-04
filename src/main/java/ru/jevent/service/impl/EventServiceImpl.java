package ru.jevent.service.impl;

import ru.jevent.model.Event;
import ru.jevent.repository.EventRepository;
import ru.jevent.service.EventService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

public class EventServiceImpl implements EventService{

    private EventRepository repository;

    @Override
    public Event save(Event event) {
        return repository.save(event);
    }

    @Override
    public void update(Event event) throws NotFoundException {
        ExceptionUtil.check(repository.save(event), event.getId());
    }

    @Override
    public Event get(long id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id), id);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id), id);
    }

    @Override
    public List<Event> getAll() {
        return repository.getAll();
    }
}
