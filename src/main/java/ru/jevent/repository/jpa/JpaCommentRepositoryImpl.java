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
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Comment get(long id) {
        return null;
    }

    @Override
    public List<Comment> getAllByVisitorId(long id) {
        return null;
    }

    @Override
    public List<Comment> getAllByEventId(long id) {
        return null;
    }

    @Override
    public List<Comment> getAllByTaskId(long id) {
        return null;
    }

    @Override
    public List<Comment> getAll() {
        return null;
    }
}
