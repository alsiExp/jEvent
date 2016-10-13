package ru.jevent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.jevent.model.Event;
import ru.jevent.repository.EventRepository;
import ru.jevent.service.EventService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

import java.util.List;

@Service
public class EventServiceImpl implements EventService{

    private EventRepository repository;

    @Autowired
    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "events", allEntries = true)
    @Override
    public Event save(Event event) {
        return repository.save(event);
    }

    @CacheEvict(value = "events", allEntries = true)
    @Override
    public void update(Event event) throws NotFoundException {
        ExceptionUtil.check(repository.save(event), event.getId());
    }

    @Override
    public Event get(long id) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id), id);
    }

    @CacheEvict(value = "events", allEntries = true)
    @Override
    public void delete(long id) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id), id);
    }

    @Cacheable("events")
    @Override
    public List<Event> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "events", allEntries = true)
    @Override
    public void dropCache() {

    }
}
