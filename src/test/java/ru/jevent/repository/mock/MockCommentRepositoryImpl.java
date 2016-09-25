package ru.jevent.repository.mock;

import org.springframework.stereotype.Repository;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Comment;
import ru.jevent.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MockCommentRepositoryImpl implements CommentRepository {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MockCommentRepositoryImpl.class);
    private static Comment comment = new Comment(1, "Comment content", MockUserRepositoryImpl.getUser(), LocalDateTime.now());

    public static Comment getComment() {
        return comment;
    }

    @Override
    public Comment save(Comment comment) {
        LOG.info("save " + comment);
        return comment;
    }

    @Override
    public boolean delete(long id) {
        LOG.info("delete " + id);
        return id != 0;
    }

    @Override
    public Comment get(long id) {
        LOG.info("get " + id);
        return comment;
    }

    @Override
    public List<Comment> getAllByVisitorId(long id) {
        LOG.info("get all comments by Visitor id = " + id);
        return null;
    }

    @Override
    public List<Comment> getAllByEventId(long id) {
        LOG.info("get all comments by Event id = " + id);
        return null;
    }

    @Override
    public List<Comment> getAllByTaskId(long id) {
        LOG.info("get all comments by Task id = " + id);
        return null;
    }

    @Override
    public List<Comment> getAll() {
        LOG.info("get all comments");
        return null;
    }
}
