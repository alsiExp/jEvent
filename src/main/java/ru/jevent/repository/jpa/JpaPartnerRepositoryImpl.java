package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jevent.model.Partner;
import ru.jevent.repository.PartnerRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaPartnerRepositoryImpl implements PartnerRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Partner save(Partner partner) {
        if(partner.isNew()) {
            em.persist(partner);
        } else {
            em.merge(partner);
        }
        return partner;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createNamedQuery(Partner.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Partner get(long id) {
        return em.find(Partner.class, id);
    }

    @Override
    public List<Partner> getAll() {
        return em.createNamedQuery(Partner.ALL_SORTED, Partner.class).getResultList();
    }
}
