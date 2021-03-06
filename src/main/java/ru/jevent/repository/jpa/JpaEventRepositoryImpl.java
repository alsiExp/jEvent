package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jevent.model.Event;
import ru.jevent.repository.EventRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static ru.jevent.util.exception.ExceptionUtil.checkUniqueResult;

@Repository
@Transactional(readOnly = true)
public class JpaEventRepositoryImpl implements EventRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Event save(Event event) {
        if(event.isNew()) {
            em.persist(event);
        } else {
            em.merge(event);
        }

        return event;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createNamedQuery(Event.DELETE).setParameter("id", id).executeUpdate() !=0;
    }

    @Override
    public Event get(long id) {
        return em.find(Event.class, id);
    }

    @Override
    public List<Event> getAll() {
        return em.createNamedQuery(Event.ALL_SORTED, Event.class).getResultList();
    }

    @Override
    public Event getByJiraId(int jiraId) {
        if(jiraId != 0) {
            return checkUniqueResult(em.createNamedQuery(Event.BY_JIRA_ID, Event.class).setParameter("jiraId", jiraId).getResultList());
        } else return null;
    }
}
