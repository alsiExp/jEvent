package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jevent.model.additionalEntity.SpeechTag;
import ru.jevent.repository.SpeechTagRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaSpeechTagRepositoryImpl implements SpeechTagRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public SpeechTag save(SpeechTag tag) {
        if(tag.isNew()){
            em.persist(tag);
        } else {
            em.merge(tag);
        }
        return tag;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createNamedQuery(SpeechTag.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public SpeechTag get(long id) {
        return em.find(SpeechTag.class, id);
    }

    @Override
    public List<SpeechTag> getAll() {
        return em.createNamedQuery(SpeechTag.ALL_SORTED, SpeechTag.class).getResultList();
    }
}
