package ru.jevent.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jevent.model.Comment;
import ru.jevent.repository.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaCommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Comment save(Comment comment) {
        if(comment.isNew()) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
        return comment;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createNamedQuery(Comment.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Comment get(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> getAll() {
        return em.createNamedQuery(Comment.GET_ALL_SORTED, Comment.class).getResultList();
    }
}
