package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.jevent.model.Event;
import ru.jevent.repository.EventRepository;

import java.util.List;
@Repository
public class JpaEventRepositoryImpl implements EventRepository {
    @Override
    public Event save(Event event) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Event get(long id) {
        return null;
    }

    @Override
    public List<Event> getAll() {
        return null;
    }
}
