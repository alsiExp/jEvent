package ru.jevent.repository.mock;

import org.springframework.stereotype.Repository;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Event;
import ru.jevent.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MockEventRepositoryImpl implements EventRepository {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MockEventRepositoryImpl.class);
    private static Event event = new Event();
    static {
        event.setId(2L);
        event.setName("Конференция Joker");
    }

    public static Event getEvent() {
        return event;
    }

    @Override
    public Event save(Event event) {
        LOG.info("save " + event);
        return this.event;
    }

    @Override
    public boolean delete(long id) {
        return id != 0;
    }

    @Override
    public Event get(long id) {
        return event;
    }

    @Override
    public List<Event> getAll() {
        return new ArrayList<Event>() {{
                add(event);
        }};
    }

    @Override
    public Event getByJiraId(int jiraId) {
        return null;
    }
}
