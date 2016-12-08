package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jevent.model.Speech;
import ru.jevent.repository.SpeechRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(readOnly = true)
public class JpaSpeechRepositoryImpl implements SpeechRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Speech save(Speech speech) {
        if(speech.isNew()) {
            em.persist(speech);
        } else {
            em.merge(speech);
        }
        return speech;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createNamedQuery(Speech.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Speech get(long id) {
        return em.find(Speech.class, id);
    }
}
