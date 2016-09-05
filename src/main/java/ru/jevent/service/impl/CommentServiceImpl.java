package ru.jevent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jevent.model.Comment;
import ru.jevent.repository.CommentRepository;
import ru.jevent.service.CommentService;
import ru.jevent.util.exception.ExceptionUtil;
import ru.jevent.util.exception.NotFoundException;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment save(Comment comment, long userId) {
        return repository.save(comment, userId);
    }

    @Override
    public Comment update(Comment comment, long userId) throws NotFoundException {
        return ExceptionUtil.check(repository.save(comment, userId), comment.getId());
    }

    @Override
    public void delete(long id, long userId) throws NotFoundException {
        ExceptionUtil.check(repository.delete(id, userId), id);
    }

    @Override
    public Comment get(long id, long userId) throws NotFoundException {
        return ExceptionUtil.check(repository.get(id, userId), id);
    }
}
