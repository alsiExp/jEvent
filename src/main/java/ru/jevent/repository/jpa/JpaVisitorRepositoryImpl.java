package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jevent.model.Visitor;
import ru.jevent.repository.VisitorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaVisitorRepositoryImpl implements VisitorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Visitor save(Visitor visitor) {
        if(visitor.isNew()) {
            em.persist(visitor);
        } else {
            em.merge(visitor);
        }
        return visitor;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createNamedQuery(Visitor.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Visitor get(long id) {
        return em.find(Visitor.class, id);
    }

    @Override
    public Visitor getByEmail(String email) {
        return null;
                //em.createNamedQuery(Visitor.BY_EMAIL, Visitor.class).setParameter(1, email).getSingleResult();
    }

    @Override
    public List<Visitor> getAll() {
        return em.createNamedQuery(Visitor.ALL_SORTED, Visitor.class).getResultList();
    }
}
