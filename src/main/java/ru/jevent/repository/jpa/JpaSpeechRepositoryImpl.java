package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jevent.model.Speech;
import ru.jevent.repository.SpeechRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.AssertFalse;
import java.util.ArrayList;
import java.util.List;

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

//    @Override
//    public List<Speech> getAll() {
//        return  em.createNamedQuery(Speech.ALL_SORTED, Speech.class).getResultList();
//    }
}
