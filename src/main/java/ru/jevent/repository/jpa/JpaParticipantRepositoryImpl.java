package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jevent.model.Participant;
import ru.jevent.repository.ParticipantRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static ru.jevent.util.exception.ExceptionUtil.checkUniqueResult;

@Repository
@Transactional(readOnly = true)
public class JpaParticipantRepositoryImpl implements ParticipantRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Participant save(Participant participant) {
        if(participant.isNew()) {
            em.persist(participant);
        } else {
            em.merge(participant);
        }
        return participant;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createNamedQuery(Participant.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Participant get(long id) {
        return em.find(Participant.class, id);
    }

    @Override
    public Participant getByEmail(String email) {
        return checkUniqueResult(em.createNamedQuery(Participant.BY_EMAIL, Participant.class).setParameter(1, email).getResultList());
    }

    @Override
    public List<Participant> getByTag(long tagId) {
        return em.createNamedQuery(Participant.BY_TAG, Participant.class).setParameter(1, tagId).getResultList();
    }

    @Override
    public List<Participant> getAll() {
        return em.createNamedQuery(Participant.ALL_SORTED, Participant.class).getResultList();
    }
}
