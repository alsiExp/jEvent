package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jevent.model.User;
import ru.jevent.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaUserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User save(User user) {
        if (user.isNew()) {
            em.persist(user);
        } else {
            em.merge(user);
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createNamedQuery(User.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public User get(long id) {
        return em.find(User.class, id);
    }

    @Override
    public User getByLogin(String login) {
        return em.createNamedQuery(User.BY_LOGIN, User.class).setParameter(1, login).getSingleResult();
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL_SORTED, User.class).getResultList();
    }

    @Override
    @Transactional
    public boolean setJiraValidCredentials(long id, boolean cred) {
        return em.createNamedQuery(User.SET_JIRA_CRED).setParameter("id", id).setParameter("cred", cred).executeUpdate() != 0;
    }
}
