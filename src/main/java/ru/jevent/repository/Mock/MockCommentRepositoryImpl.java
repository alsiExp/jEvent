package ru.jevent.repository.Mock;

import org.springframework.stereotype.Repository;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Comment;
import ru.jevent.repository.CommentRepository;

@Repository
public class MockCommentRepositoryImpl implements CommentRepository {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MockCommentRepositoryImpl.class);

    @Override
    public Comment save(Comment comment, long userId) {
        LOG.info("save" + comment);
        return comment;
    }

    @Override
    public boolean delete(long id, long userId) {
        LOG.info("delete" + id);
        return id != 0;
    }

    @Override
    public Comment get(long id, long userId) {
        LOG.info("get" + id);
        return null;
    }
}
